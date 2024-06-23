export const csrf_token = document
  .querySelector('meta[name="_csrf"]')
  .getAttribute("content")
export const csrf_header = document
  .querySelector('meta[name="_csrf_header"]')
  .getAttribute("content")
