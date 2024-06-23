import MiniCssExtractPlugin from "mini-css-extract-plugin"
import path from "path"
import fs from "fs"
import { fileURLToPath } from "url"
import webpack from "webpack"

const __dirname = path.dirname(fileURLToPath(import.meta.url))
const entries = {}

// Recursively read all JavaScript files in a directory
function getAllFiles(dirPath, arrayOfFiles = []) {
  const files = fs.readdirSync(dirPath)

  files.forEach((file) => {
    const fullPath = path.join(dirPath, file)
    if (fs.statSync(fullPath).isDirectory()) {
      arrayOfFiles = getAllFiles(fullPath, arrayOfFiles)
    } else {
      if (file.endsWith(".js")) {
        arrayOfFiles.push(fullPath)
      }
    }
  })

  return arrayOfFiles
}

const jsFiles = getAllFiles("./src/main/js/")

jsFiles.forEach((file) => {
  // Create a clean entry name using only the filename
  const entryName = path.basename(file, ".js")
  entries[entryName] = [path.resolve(__dirname, file)] // Ensure full path
})

const config = {
  entry: entries,
  output: {
    filename: "bundle-[name].js",
    path: path.resolve(__dirname, "src/main/resources/static/js"),
    clean: true,
  },
  devtool: "source-map",
  mode: "development",
  plugins: [
    new MiniCssExtractPlugin({
      filename: "../css/bundle-[name].css",
    }),
    new webpack.ProvidePlugin({
      process: "process/browser",
    }),
    new webpack.DefinePlugin({
      "process.env": JSON.stringify(process.env),
    }),
  ],
  module: {
    rules: [
      {
        test: /\.s?css$/i,
        use: [MiniCssExtractPlugin.loader, "css-loader", "sass-loader"],
      },
      {
        test: /\.(png|svg|jpe?g|gif)$/i,
        type: "asset/resource",
      },
      {
        test: /\.(woff2?|eot|ttf|otf)$/i,
        type: "asset/resource",
      },
      {
        test: /\.js$/,
        exclude: /node_modules/,
        use: {
          loader: "babel-loader",
          options: {
            presets: ["@babel/preset-env"],
          },
        },
      },
    ],
  },
  experiments: {
    topLevelAwait: true,
  },
  resolve: {
    extensions: [".js"],
    fallback: {
      fs: false,
      path: "path-browserify",
      process: "process/browser",
    },
  },
}

export default config
