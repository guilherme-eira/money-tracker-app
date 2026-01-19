document.addEventListener('DOMContentLoaded', function () {
    const deleteGoalModal = document.getElementById('deleteGoalModal');
    if (deleteGoalModal) {
        deleteGoalModal.addEventListener('show.bs.modal', event => {
            const button = event.relatedTarget;
            const goalId = button.getAttribute('data-id');
            const form = deleteGoalModal.querySelector('#confirmDeleteGoalForm');
            form.action = `/goals/${goalId}/delete`;
        });
    }
});