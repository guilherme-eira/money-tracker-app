document.addEventListener('DOMContentLoaded', function () {
    const input = document.getElementById('deleteConfirmationInput');
    const btn = document.getElementById('confirmDeleteBtn');
    const modalElement = document.getElementById('deleteAccountModal');
    const confirmationWord = 'excluir-conta';

    if (!input || !btn || !modalElement) {
        return;
    }

    input.addEventListener('input', function () {
        if (this.value === confirmationWord) {
            btn.disabled = false;
        } else {
            btn.disabled = true;
        }
    });

    modalElement.addEventListener('hidden.bs.modal', function () {
        input.value = '';
        btn.disabled = true;
    });
});