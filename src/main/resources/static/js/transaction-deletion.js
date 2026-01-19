document.addEventListener('DOMContentLoaded', function () {
    const deleteModal = document.getElementById('deleteTransactionModal');
    if (deleteModal) {
        deleteModal.addEventListener('show.bs.modal', event => {
            const button = event.relatedTarget;
            const transactionId = button.getAttribute('data-id');
            const form = deleteModal.querySelector('#confirmDeleteForm');
            form.action = `/transactions/${transactionId}/delete`;
        });
    }
});