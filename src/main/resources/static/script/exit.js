function logout() {
    fetch('/web/logout', {
        method: 'POST',
        credentials: 'include'
    })
    .then(response => {
        if (response.ok) {
            window.location.href = "/web/login"; // Перенаправляем на страницу входа
        } else {
            console.error('Ошибка при выходе из системы:', response.statusText);
            alert('Ошибка при выходе. Попробуйте еще раз.');
        }
    })
    .catch(error => {
        console.error('Ошибка сети:', error);
    });
}