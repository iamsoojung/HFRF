<template>
    <div ref="chart" style="width: 100%; height: 300px;"></div>
</template>

<script>
import { onMounted, ref, watch } from 'vue';

export default {
    props: {
        detailApt: {
            type: Array,
            required: true,
        },
    },
    setup(props) {
        const chart = ref(null);

        onMounted(() => {
            // Google Charts 라이브러리 로드
            const script = document.createElement('script');
            script.type = 'text/javascript';
            script.src = 'https://www.gstatic.com/charts/loader.js';
            script.onload = () => {
                google.charts.load('current', { packages: ['corechart'] });
                google.charts.setOnLoadCallback(drawChart);
            };
            document.head.appendChild(script);
        });

        const drawChart = () => {
            if (!chart.value || !props.detailApt.length) return;

            // 차트를 그리기 위한 데이터 설정
            const data = new google.visualization.DataTable();
            data.addColumn('string', 'Date');
            data.addColumn('number', 'Price');
            props.detailApt.reverse().forEach(item => {
                const date = `${item.dealYear}-${item.dealMonth}`;
                data.addRow([date, parseInt(item.dealAmount.replace(/,/g, ''))]);
            });

            props.detailApt = props.detailApt.reverse();
            // 차트 옵션 설정
            const options = {
                title: '실거래가',
                width: 600,
                height: 300,
                hAxis: {
                    title: '날짜',
                },
                vAxis: {
                    title: '가격',
                },
                animation: {
                    startup: true,
                    duration: 800,
                    easing: 'linear'
                },
                legend: 'none',
                series: {
                    0: { color: 'DD5566' }
                },
                pointSize: 3,
            };

            // 차트를 그릴 div 요소를 참조
            const chartElement = chart.value;

            // 차트 생성
            const chartInstance = new google.visualization.LineChart(chartElement);
            chartInstance.draw(data, options);
        };

        watch(() => props.detailApt, drawChart);

        return {
            chart
        };
    }
};
</script>

<style scoped></style>
