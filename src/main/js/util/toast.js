import { Toast } from "bootstrap"

export function showToast(message) {
  let toastElement = document.querySelector(".toast")
  let toastBody = toastElement.querySelector(".toast-body")
  toastBody.innerText = message

  let toast = new Toast(toastElement, {
    autohide: false,
  })
  toast.show()
}
