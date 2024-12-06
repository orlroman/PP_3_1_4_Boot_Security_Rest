$(async function () {
    await getUserTable();
})

async function getUserTable() {
    const response = await fetch('api/user/auth');
    const user = await response.json();

    document.getElementById('id').textContent = user.id;
    document.getElementById('firsName').textContent = user.firstName;
    document.getElementById('lastName').textContent = user.lastName;
    document.getElementById('age').textContent = user.age;
    document.getElementById('email').textContent = user.email;
    document.getElementById('roles').textContent = user.roles
        .map(role => role.title.replace('ROLE_', ''))
        .join(' ');
}
