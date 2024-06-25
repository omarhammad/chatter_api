export function direct_access_not_allowed() {
    const body = document.querySelector('body');
    body.innerHTML = null;
    body.innerHTML = '<div class="alert alert-danger text-center"> DIRECT ACCESS IS NOT ALLOWED ! </div>';
}
