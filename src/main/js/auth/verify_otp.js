import {decryptData, encryptData} from "../util/crypto.js";
import {HttpStatus} from "../util/http_status.js";
import {showToast} from "../util/toast.js";
import {direct_access_not_allowed} from "../util/access_componenet.js";

const verify_btn = document.querySelector("#verify_btn");
const resend_btn = document.querySelector('#resend_btn');
const code_inputs = document.querySelector('#code-container').querySelectorAll('input');

document.addEventListener('DOMContentLoaded', prepare_page)

function prepare_page() {


    code_inputs.forEach(input => {
        input.addEventListener('input', function () {
            this.value = this.value.replace(/[^0-9]/g, '');
            if (this.value.length > 1) {
                this.value = this.value.slice(0, 1);
            }
        });
    });

    let source = sessionStorage.getItem('source')
    if (source === null || source === undefined) {
        direct_access_not_allowed();
        return;
    } else {
        source = decryptData(source);
        if (source !== 'auth') {
            direct_access_not_allowed();
            return
        }
    }
    document.querySelector("#to_email").innerText = decryptData(sessionStorage.getItem('email'));
}


verify_btn.addEventListener('click', verify_otp)

async function verify_otp() {
    const email = decryptData(sessionStorage.getItem('email'));
    const code =
        Array.from(code_inputs).reduce((acc, input) => {
            return acc + input.value;
        }, '')

    const response = await fetch('/api/auth/verify-otp',
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accepts': 'text/plain'
            },
            body: JSON.stringify({
                email: email,
                code: code
            })
        })

    if (response.status === HttpStatus.UNAUTHORIZED) {
        showToast("Code entered is wrong, try again!");
    } else if (response.status === HttpStatus.GONE) {
        showToast("Code expired, press on 'Resend' !");
    } else if (response.status === HttpStatus.NOT_FOUND) {
        showToast("There is no OTP 'sign in/up again!'");
    } else if (response.status === HttpStatus.OK) {
        const auth_type = await response.text();
        if (auth_type === 'SIGN_IN') {
            window.location.href = '/chats';
            sessionStorage.clear();
        } else if (auth_type === 'SIGN_UP') {
            window.location.href = '/chatters/complete-profile';
            sessionStorage.clear();
            sessionStorage.setItem('source', encryptData('SIGN_UP'))
        }
    }


}

resend_btn.addEventListener('click', resend_otp);

async function resend_otp() {
    const email = decryptData(sessionStorage.getItem('email'));

    const response = await fetch("/api/auth/send-otp",
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'text/plain'
            },
            body: JSON.stringify({
                email: email
            })
        });

    if (response.status === HttpStatus.CREATED) {
        resend_btn.textContent = 'Sent'
        resend_btn.disabled = true;
        setTimeout(() => {
            resend_btn.textContent = 'Resend';
            resend_btn.disabled = false;

        }, 60000);
    }
}



