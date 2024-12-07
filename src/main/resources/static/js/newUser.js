$(async function () {
    await addNewUser();
})

const userFetchService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },

    addNewUser: async (user) => await fetch('http://localhost:8080/api/new',
        {method: 'POST', headers: userFetchService.head, body: JSON.stringify(user)}),
}

async function addNewUser() {
    $('#addNewUserButton').click(async () => {
        event.preventDefault();
        let addUserForm = $('#new-user-form')
        let firstName = addUserForm.find('#firstName').val().trim();
        let lastName = addUserForm.find('#lastName').val().trim();
        let age = addUserForm.find('#age').val().trim();
        let email = addUserForm.find('#email').val().trim();
        let password = addUserForm.find('#password').val().trim();

        let selectedRoles = addUserForm.find('#roles').val();
        let roles = selectedRoles.map(role => {
            let roleId;
            if (role === 'ADMIN') {
                roleId = 1;
            } else if (role === 'USER') {
                roleId = 2;
            }
            return {
                id: roleId,
                title: "ROLE_" + role,
                authority: "ROLE_" + role
            }
        });

        let data = {
            firstName: firstName,
            lastName: lastName,
            email: email,
            password: password,
            age: age,
            roles: roles
        };

        const response = await userFetchService.addNewUser(data);
        if (response.ok) {
            window.location.href = '/admin';
        } else {
            let body = await response.json();
            let alert = `<div class="alert alert-danger alert-dismissible fade show col-12" id="messageError">
                            ${body.message}
                            <button type="button" class="close" data-bs-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
            addUserForm.prepend(alert)
        }
    })
}