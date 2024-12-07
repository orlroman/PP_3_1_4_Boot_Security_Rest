document.addEventListener('DOMContentLoaded', () => {
    fetch('http://localhost:8080/api/user/auth')
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch user data')
            }
            return response.json();
        })
        .then(data => {
            document.getElementById('user-email').textContent=data.email;
             // Объединяем роли в строку через пробел
            document.getElementById('user-roles').textContent = data.roles
                .map(role => role.title.replace('ROLE_', '')) // Убираем префикс "ROLE_"
                .join(' ');
        })
        .catch(error => {
            console.error('Error fetching user data:', error);
        })
})