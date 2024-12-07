$(async function () {
    await getUserTable();
    await getLeftNav();
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

async function getLeftNav () {
    const response = await fetch('api/user/auth');
    const authUser = await response.json();

    const nav = document.getElementById('left-nav');
    const roles = authUser.roles.map(role => role.title);

    if (roles.includes('ROLE_ADMIN')) {
        const adminItem = document.createElement('li');
        adminItem.className = 'nav-item ms-2';
        adminItem.innerHTML = `<a class="nav-link" href="/admin">Admin</a>`;
        nav.appendChild(adminItem);
    }

    const userItem = document.createElement('li');
    userItem.className = 'nav-item ms-2';
    userItem.innerHTML = `<a class="nav-link active" href="/user">User</a>`;
    nav.appendChild(userItem);

}
