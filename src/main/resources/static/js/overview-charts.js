document.addEventListener('DOMContentLoaded', () => {

    const currencyFormatter = new Intl.NumberFormat('pt-BR', {
        style: 'currency',
        currency: 'BRL'
    });

    const backgroundColors = [
        '#4e73df', '#1cc88a', '#36b9cc', '#f6c23e', '#e74a3b',
        '#858796', '#5a5c69', '#6610f2', '#fd7e14', '#20c997'
    ];

    function prepareChartContainer(containerId) {
        const container = document.getElementById(containerId);
        if (!container) return null;
        const rawJson = container.getAttribute('data-chart-data');
        let chartData = null;
        try {
            chartData = rawJson ? JSON.parse(rawJson) : null;
        } catch (e) {
            console.error("Erro ao ler JSON do gráfico", e);
        }

        const hasData = chartData && chartData.data && chartData.data.length > 0;
        const totalValue = hasData ? chartData.data.reduce((a, b) => a + b, 0) : 0;

        if (!hasData || totalValue === 0) {
            container.classList.add('d-flex', 'align-items-center', 'justify-content-center', 'flex-column', 'bg-body-tertiary');
            container.innerHTML = `
                <div class="text-center text-body-tertiary">
                    <i class="bi bi-bar-chart-line d-block fs-1 mb-2 opacity-50"></i>
                    <span class="fw-semibold small">Sem lançamentos neste período</span>
                </div>
            `;
            return null;
        }

        container.classList.remove('d-flex', 'align-items-center', 'justify-content-center', 'bg-body-tertiary');
        container.innerHTML = '';
        const canvas = document.createElement('canvas');
        canvas.style.width = '100%';
        canvas.style.height = '100%';
        container.appendChild(canvas);

        return {
            ctx: canvas.getContext('2d'),
            data: chartData
        };
    }

    const categorySetup = prepareChartContainer('chart-expenses-category');

    if (categorySetup) {
        new Chart(categorySetup.ctx, {
            type: 'doughnut',
            data: {
                labels: categorySetup.data.labels,
                datasets: [{
                    data: categorySetup.data.data,
                    backgroundColor: backgroundColors,
                    hoverOffset: 4,
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        position: 'bottom',
                        labels: { usePointStyle: true, padding: 20 }
                    },
                    tooltip: {
                        callbacks: {
                            label: function(context) {
                                let label = context.label || '';
                                let value = context.raw || 0;
                                return `${label}: ${currencyFormatter.format(value)}`;
                            }
                        }
                    }
                }
            }
        });
    }

    const weekSetup = prepareChartContainer('chart-expenses-week');

    if (weekSetup) {
        new Chart(weekSetup.ctx, {
            type: 'bar',
            data: {
                labels: weekSetup.data.labels,
                datasets: [{
                    label: 'Gasto Diário',
                    data: weekSetup.data.data,
                    backgroundColor: '#6b54ec',
                    borderRadius: 4,
                    barPercentage: 0.6
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    y: {
                        beginAtZero: true,
                        grid: { borderDash: [2, 4] },
                        ticks: {
                            callback: (value) => currencyFormatter.format(value)
                        }
                    },
                    x: {
                        grid: { display: false }
                    }
                },
                plugins: {
                    legend: { display: false },
                    tooltip: {
                        callbacks: {
                            label: function(context) {
                                return currencyFormatter.format(context.raw);
                            }
                        }
                    }
                }
            }
        });
    }
});