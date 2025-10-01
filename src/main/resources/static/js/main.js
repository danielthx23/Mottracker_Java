// Main JavaScript for Mottracker

document.addEventListener('DOMContentLoaded', function() {
    // Ensure sidebar links are clickable
    var sidebarLinks = document.querySelectorAll('.sidebar .nav-link');
    sidebarLinks.forEach(function(link) {
        link.style.pointerEvents = 'auto';
        link.style.cursor = 'pointer';
        link.style.zIndex = '1000';
        link.style.display = 'block';
        link.style.position = 'relative';
        
        // Add click event listener to ensure navigation works
        link.addEventListener('click', function(e) {
            console.log('Sidebar link clicked:', this.href);
            // Allow default navigation
            return true;
        });
    });
    
    // Also ensure nav-items are clickable
    var navItems = document.querySelectorAll('.sidebar .nav-item');
    navItems.forEach(function(item) {
        item.style.pointerEvents = 'auto';
        item.style.cursor = 'pointer';
    });

    // Initialize tooltips
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });

    // Initialize popovers
    var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
    var popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
        return new bootstrap.Popover(popoverTriggerEl);
    });

    // Auto-hide alerts after 5 seconds
    setTimeout(function() {
        var alerts = document.querySelectorAll('.alert');
        alerts.forEach(function(alert) {
            var bsAlert = new bootstrap.Alert(alert);
            bsAlert.close();
        });
    }, 5000);

    // Form validation
    var forms = document.querySelectorAll('.needs-validation');
    Array.prototype.slice.call(forms).forEach(function(form) {
        form.addEventListener('submit', function(event) {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        }, false);
    });

    // Confirm delete actions
    var deleteButtons = document.querySelectorAll('[data-confirm-delete]');
    deleteButtons.forEach(function(button) {
        button.addEventListener('click', function(event) {
            if (!confirm('Tem certeza que deseja excluir este item?')) {
                event.preventDefault();
            }
        });
    });

    // Format currency inputs
    var currencyInputs = document.querySelectorAll('input[data-currency]');
    currencyInputs.forEach(function(input) {
        input.addEventListener('input', function() {
            var value = this.value.replace(/\D/g, '');
            var formattedValue = (value / 100).toLocaleString('pt-BR', {
                style: 'currency',
                currency: 'BRL'
            });
            this.value = formattedValue;
        });
    });

    // Format CPF input
    var cpfInputs = document.querySelectorAll('input[data-cpf]');
    cpfInputs.forEach(function(input) {
        input.addEventListener('input', function() {
            var value = this.value.replace(/\D/g, '');
            if (value.length <= 11) {
                value = value.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4');
                this.value = value;
            }
        });
    });

    // Format phone input
    var phoneInputs = document.querySelectorAll('input[data-phone]');
    phoneInputs.forEach(function(input) {
        input.addEventListener('input', function() {
            var value = this.value.replace(/\D/g, '');
            if (value.length <= 11) {
                value = value.replace(/(\d{2})(\d{5})(\d{4})/, '($1) $2-$3');
                this.value = value;
            }
        });
    });

    // Dashboard statistics animation
    var statNumbers = document.querySelectorAll('.stat-number');
    statNumbers.forEach(function(stat) {
        var target = parseInt(stat.textContent);
        var current = 0;
        var increment = target / 50;
        
        var timer = setInterval(function() {
            current += increment;
            if (current >= target) {
                current = target;
                clearInterval(timer);
            }
            stat.textContent = Math.floor(current);
        }, 30);
    });
});

// Utility functions
function showAlert(message, type = 'info') {
    var alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${type} alert-dismissible fade show`;
    alertDiv.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;
    
    var container = document.querySelector('.container-fluid main') || document.body;
    container.insertBefore(alertDiv, container.firstChild);
    
    setTimeout(function() {
        var bsAlert = new bootstrap.Alert(alertDiv);
        bsAlert.close();
    }, 5000);
}

function formatCurrency(value) {
    return new Intl.NumberFormat('pt-BR', {
        style: 'currency',
        currency: 'BRL'
    }).format(value);
}

function formatDate(date) {
    return new Intl.DateTimeFormat('pt-BR').format(new Date(date));
}

function formatDateTime(date) {
    return new Intl.DateTimeFormat('pt-BR', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit'
    }).format(new Date(date));
}

