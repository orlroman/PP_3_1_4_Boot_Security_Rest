$(async function (){
    await getLeftNav();
})

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