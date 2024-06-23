import {HttpStatus} from "../util/http_status.js";
import {showToast} from "../util/toast.js";
import {encryptData} from "../util/crypto.js";

const signin_button = document.querySelector("#signin");

signin_button.addEventListener('click', send_otp);

async function send_otp() {

    const email = document.querySelector("#email").value;

    const bodyJson = {
        email: email
    }

    const response = await fetch("/api/auth/send-otp",
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'text/plain'
            },
            body: JSON.stringify(bodyJson)
        });

    if (response.status === HttpStatus.CREATED) {
        sessionStorage.setItem('source', encryptData("auth"));
        sessionStorage.setItem('email', encryptData(email));
        window.location.href = '/auth/verify';
    } else {
        const error_msg = await response.text();
        showToast(error_msg);
    }


}