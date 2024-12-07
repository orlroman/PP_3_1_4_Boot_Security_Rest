$(async function (){
    await getUsersTable();
    await getDefaultModal();
})

const userFetchService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },

    findAllUsers: async () => await fetch('api/users'),
    findOneUser: async (id) => await fetch(`api/users/${id}`),
    updateUser: async (user, id) => await fetch(`api/edit/${id}`,
        {method: 'POST', headers: userFetchService.head, body: JSON.stringify(user)}),
    deleteUser: async (id) => await fetch(`api/delete/${id}`,
        {method: 'POST', headers: userFetchService.head})
}

async function getUsersTable() {
    let table = $('#users-table tbody');
    table.empty();

    await userFetchService.findAllUsers()
        .then(response => response.json())
        .then(users => {
            users.forEach(user => {
                const roles = user.roles.map(role =>
                    role.title.replace('ROLE_', ''))
                    .join(' ');
                const tableFilling = `$(
                        <tr class="align-middle">
                            <td>${user.id}</td>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>${user.age}</td>
                            <td>${user.email}</td>
                            <td>${roles}</td>
                            <td>
                                <button type="button" data-userid="${user.id}" data-action="edit"
                                    class="btn btn-info text-white" 
                                    data-bs-toggle="modal" data-bs-target='#editUserModal'>Edit</button>
                            </td>
                            <td>
                                <button type="button" data-userid="${user.id}" data-action="delete" 
                                    class="btn btn-danger text-white"
                                    data-bs-toggle="modal" data-bs-target='#deleteUserModal'>Delete</button>
                            </td>
                        </tr>
                )`;
                table.append(tableFilling);
            })
        })

    // action
    $("#users-table").find('button').on('click', (event) => {
        let defaultModal = $('#someDefaultModal');

        let targetButton = $(event.target);
        let buttonUserId = targetButton.attr('data-userid');
        let buttonAction = targetButton.attr('data-action');

        defaultModal.attr('data-userid', buttonUserId);
        defaultModal.attr('data-action', buttonAction);
        defaultModal.modal('show');
    })
}

// modal
async function getDefaultModal() {
    $('#someDefaultModal').modal({
        keyboard: true,
        backdrop: "static",
        show: false
    }).on("show.bs.modal", (event) => {
        let thisModal = $(event.target);
        let userid = thisModal.attr('data-userid');
        let action = thisModal.attr('data-action');
        switch (action) {
            case 'edit':
                editUser(thisModal, userid);
                break;
            case 'delete':
                deleteUser(thisModal, userid);
                break;
        }
    }).on("hidden.bs.modal", (e) => {
        let thisModal = $(e.target);
        thisModal.find('.modal-title').html('');
        thisModal.find('.modal-body').html('');
        thisModal.find('.modal-footer').html('');
    })
}

// edit
async function editUser(modal, id) {
    let editUser = await userFetchService.findOneUser(id);
    let user = editUser.json();

    modal.find('.modal-title').html('Edit user');

    let closeButton = `<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>`;
    let editButton = `<button type="submit" class="btn btn-primary" id="editButton">Edit</button>`;
    modal.find('.modal-footer').append(closeButton);
    modal.find('.modal-footer').append(editButton);

    user.then(user => {
        let bodyForm = `
                    <form id="editUserForm">
                        <div class="mb-3">
                            <label class="form-label fw-bold" for="editId">ID</label>
                            <input class="form-control w-50 mx-auto bg-light" type="text"
                                id="editId"  value="${user.id}" disabled>
                        </div>
                        <div class="mb-3">
                            <label class="form-label fw-bold" for="editFirstName">First name</label>
                            <input class="form-control w-50 mx-auto" type="text" 
                                id="editFirstName" value="${user.firstName}">
                        </div>
                        <div class="mb-3">
                            <label class="form-label fw-bold" for="editLastName">Last name</label>
                            <input class="form-control w-50 mx-auto" type="text" 
                                id="editLastName" value="${user.lastName}">
                        </div>
                        <div class="mb-3">
                            <label class="form-label fw-bold" for="editAge">Age</label>
                            <input class="form-control w-50 mx-auto" type="number" 
                                id="editAge" value="${user.age}">
                        </div>
                        <div class="mb-3">
                            <label class="form-label fw-bold" for="editEmail">Email</label>
                            <input class="form-control w-50 mx-auto" type="email" 
                                id="editEmail" value="${user.email}">
                        </div>
                        <div class="mb-3">
                            <label class="form-label fw-bold" for="editPassword">Password</label>
                            <input class="form-control w-50 mx-auto" type="password" 
                                id="editPassword">
                        </div>
                        <div class="mb-3">
                            <label for="editRoles" class="form-label fw-bold">Role</label>
                            <select class="form-select w-50 mx-auto" id="editRoles" name="roles" multiple>
                                <option value="ADMIN">ADMIN</option>
                                <option value="USER">USER</option>
                            </select>
                        </div>
                    </form>
        `;
        modal.find('.modal-body').append(bodyForm);
        let userSelectedRoles = user.roles.map(role => role.title.replace('ROLE_', ''));
        $('#editRoles').val(userSelectedRoles);
    })



    $("#editButton").on('click', async () => {

        let id = modal.find("#editId").val().trim();
        let firstName = modal.find('#editFirstName').val().trim();
        let lastName = modal.find('#editLastName').val().trim();
        let age = modal.find('#editAge').val().trim();
        let email = modal.find('#editEmail').val().trim();
        let password = modal.find('#editPassword').val().trim();

        let selectedRoles = modal.find('#editRoles').val();
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
            id: id,
            firstName: firstName,
            lastName: lastName,
            email: email,
            password: password,
            age: age,
            roles: roles
        };

        const response = await userFetchService.updateUser(data, id);

        if (response.ok) {
            await getUsersTable();
            modal.modal('hide');
        } else {
            let body = await response.json();
            let alert = `<div class="alert alert-danger alert-dismissible fade show col-12" role="alert" id="messageError">
                            ${body.message}
                            <button type="button" class="close" data-bs-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
            modal.find('.modal-body').prepend(alert);
        }
    })
}


// delete
async function deleteUser(modal, id) {
    let deleteUser = await userFetchService.findOneUser(id);
    let user = deleteUser.json();

    modal.find('.modal-title').html('Delete user');

    let closeButton = `<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>`;
    let editButton = `<button type="submit" class="btn btn-danger" id="deleteButton">Delete</button>`;
    modal.find('.modal-footer').append(closeButton);
    modal.find('.modal-footer').append(editButton);

    user.then(user => {
        let bodyForm = `
                    <form id="deleteUserForm">
                        <div class="mb-3">
                            <label class="form-label fw-bold" for="deleteId">ID</label>
                            <input class="form-control w-50 mx-auto bg-light" type="text"
                                id="deleteId"  value="${user.id}" disabled>
                        </div>
                        <div class="mb-3">
                            <label class="form-label fw-bold" for="deleteFirstName">First name</label>
                            <input class="form-control w-50 mx-auto" type="text"
                                id="deleteFirstName" value="${user.firstName}" disabled>
                        </div>
                        <div class="mb-3">
                            <label class="form-label fw-bold" for="deleteLastName">Last name</label>
                            <input class="form-control w-50 mx-auto" type="text"
                                id="deleteLastName" value="${user.lastName}" disabled>
                        </div>
                        <div class="mb-3">
                            <label class="form-label fw-bold" for="deleteAge">Age</label>
                            <input class="form-control w-50 mx-auto" type="number"
                                id="deleteAge" value="${user.age}" disabled>
                        </div>
                        <div class="mb-3">
                            <label class="form-label fw-bold" for="deleteEmail">Email</label>
                            <input class="form-control w-50 mx-auto" type="email"
                                id="deleteEmail" value="${user.email}" disabled>
                        </div>
                        <div class="mb-3">
                            <label for="editRoles" class="form-label fw-bold">Role</label>
                            <select class="form-select w-50 mx-auto" id="deleteRoles" name="roles" multiple disabled>
                                <option value="ADMIN">ADMIN</option>
                                <option value="USER">USER</option>
                            </select>
                        </div>
                    </form>
        `;
        modal.find('.modal-body').append(bodyForm);
        let userSelectedRoles = user.roles.map(role => role.title.replace('ROLE_', ''));
        $('#deleteRoles').val(userSelectedRoles);
    })

    $("#deleteButton").on('click', async () => {

        const response = await userFetchService.deleteUser(id);

        if (response.ok) {
            await getUsersTable();
            modal.modal('hide');
        } else {
            let body = await response.json();
            let alert = `<div class="alert alert-danger alert-dismissible fade show col-12" role="alert" id="messageError">
                            ${body.message}
                            <button type="button" class="close" data-bs-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
            modal.find('.modal-body').prepend(alert);
        }
    })
}





