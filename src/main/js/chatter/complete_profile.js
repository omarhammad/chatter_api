import '../../scss/camera_input.scss'
import {HttpStatus} from "../util/http_status.js";
import {csrf_header, csrf_token} from "../util/csrf.js";
import {decryptData} from "../util/crypto.js";


document.querySelector('#profile_pic').addEventListener('change', function (event) {
    const file = event.target.files[0];

    const validImageTypes = ['image/jpeg', 'image/png', 'image/jpg'];
    if (!validImageTypes.includes(file.type)) {
        alert("Invalid file type. Please select an image file (jpeg, png, gif).");
        return;
    }

    if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            const imagePreview = document.querySelector('#profile_pic_element');
            imagePreview.src = e.target.result;
        };
        reader.readAsDataURL(file);
    }
});

document.addEventListener('DOMContentLoaded', check_redirect_source);

async function check_redirect_source() {
    let source = sessionStorage.getItem('source');
    sessionStorage.removeItem('source');

    if (source === null || source === undefined) {
        window.location.href = '/chats';

    }
    source = decryptData(source);
    if (source !== 'SIGN_UP') {
        window.location.href = '/chats';
    }

}


const done_btn = document.querySelector('#done_btn');

done_btn.addEventListener('click', updateChatterInfo)

async function updateChatterInfo() {

    const formData = new FormData();

    const current_user_response = await fetch('/api/auth/current-user');
    if (current_user_response.status === HttpStatus.UNAUTHORIZED) {
        window.location.href = '/auth';
    } else if (current_user_response.status === HttpStatus.OK) {
        const user = await current_user_response.json();

        const profile_pic = document.querySelector("#profile_pic").files[0];


        formData.append('id', user.id);
        formData.append('profilePic', profile_pic)
        formData.append('bio', document.querySelector('#bio').value)
        formData.append('username', document.querySelector('#username').value)
        formData.append('email', user.email);

        await update_request(formData);

    }

}

async function update_request(formData) {


    const response = await fetch(`/api/chatters/${formData.get('id')}`,
        {
            method: 'PUT',
            headers: {
                [csrf_header]: csrf_token
            },
            body: formData
        }
    );


    if (response.status === HttpStatus.UNAUTHORIZED) {
        document.cookie = 'JSESSIONID=;expires=Thu, 01 Jan 1970 00:00:00 GMT;path=/';
        window.location.href = '/auth';
    } else if (response.status === HttpStatus.CONFLICT) {
        console.log('Error while updating chatter info. try again later!');
        window.location.href = '/chats';
    } else if (response.status === HttpStatus.NOT_FOUND) {
        console.log('Chatter Not Found!');
        document.cookie = 'JSESSIONID=;expires=Thu, 01 Jan 1970 00:00:00 GMT;path=/';
        window.location.href = '/auth';
    } else if (response.status === HttpStatus.NO_CONTENT) {
        window.location.href = '/chats';
    }

}





