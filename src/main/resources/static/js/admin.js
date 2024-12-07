$(async function (){
    await getUsersTable();
    await addNewUser();
})

const userFetchService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },

    findAllUsers: async () => await fetch('api/users'),
    findOneUser: async (id) => await fetch(`api/users/${id}`),
    addNewUser: async (user) => await fetch('api/new',
        {method: 'POST', headers: userFetchService.head, body: JSON.stringify(user)}),
    updateUser: async (user, id) => await fetch(`api/edit/${id}`,
        {method: 'PUT', headers: userFetchService.head, body: JSON.stringify(user)}),
    deleteUser: async (id) => await fetch(`api/delete/${id}`,
        {method: 'DELETE', headers: userFetchService.head})
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
                                <button type="button" data-bs-userid="${user.id}" class="btn btn-info text-white" 
                                data-bs-toggle="modal" data-bs-target='#editUserModal'>Edit</button>
                            </td>
                            <td>
                                <button type="button" data-bs-userid="${user.id}" class="btn btn-danger text-white"
                                 data-bs-toggle="modal" data-bs-target='#deleteUserModal'>Delete</button>
                            </td>
                        </tr>
                )`;
                table.append(tableFilling);
            })
        })
}

