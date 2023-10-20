<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from 'axios';
import * as echarts from 'echarts/core'
import {
    TitleComponent,
    ToolboxComponent,
    TooltipComponent,
    GridComponent,
    GridComponentOption,
    LegendComponent,
    LegendComponentOption
} from 'echarts/components';
import { BarChart, BarSeriesOption } from 'echarts/charts';
import { CanvasRenderer } from 'echarts/renderers';
import Quessionnaire from './Quessionnaire.vue';

echarts.use([TitleComponent,
    ToolboxComponent,
    TooltipComponent,
    GridComponent,
    LegendComponent,
    BarChart,
    CanvasRenderer]);
type EChartsOption = echarts.ComposeOption<
    | GridComponentOption
    | LegendComponentOption
    | BarSeriesOption
>;

let myChart: echarts.ECharts | null = null;
const countries = ref<any[]>([]);
const totalPop = ref(0);
const top5countries = ref<string[]>([]);
const populationData = ref([]);
const selectedCountry = ref();
const selectedCountryIndex = ref(-1);
const lastCountryIndex = ref(-1);
const yAxisData = ref<any[]>([]);
const seriesData = ref<any[]>([]);
const option3 = ref<any[]>([]);
const totalPopValue = ref('Total(7,503,400)');

onMounted(() => {
    getBarCountries();
    getTopFiveCountry();
});

const getBarCountries = () => {
    const apiUrl = 'https://harmonyoz.azurewebsites.net/getCountries';
    axios
        .get(apiUrl)
        .then(response => {
            countries.value = response.data
            option3.value = Array.from({ length: countries.value.length }).map((_, idx) => ({
                value: countries.value[idx],
                label: countries.value[idx].replace(/_/g, ' '), 
            }))
        })
}

const getTotalPop = () => {
    const apiUrl = 'https://harmonyoz.azurewebsites.net/getTotalPop';
    axios
        .get(apiUrl)
        .then(response => {
            totalPop.value = response.data
        })
}

const option: EChartsOption = {
    title: {
        text: "Current migrant population in Australia\n" + totalPopValue.value,
        textStyle: {
            color: '#621708'
        },
        left: 'right'
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'shadow'
        },
        formatter: function (params: any) {
            const modifiedValue = (params[0].data.value / totalPop.value * 100).toFixed(2) + '%';
            if (modifiedValue === '0%') {
                return `Migrants from ${params[0].name} less than 1% of the total migrants.`;
            }
            return `Migrants from ${params[0].name}<br>make up ${modifiedValue} of the total migrants.`;
        }
    },
    legend: {},
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: {
        type: 'value',
        name: 'Population',
        position: 'left'
    },
    yAxis: {
        type: 'category',
        data: yAxisData.value
    },
    series: [
        {
            data: seriesData.value,
            type: 'bar',
            stack: 'Total',
        }
    ]
};

interface CountryData {
    country: string;
    population: number;
    subName: string;
}

const getTopFiveCountry = () => {
    getTotalPop();
    const apiUrl = 'https://harmonyoz.azurewebsites.net/getTopFiveCountry';
    axios
        .get(apiUrl)
        .then(response => {
            const data = response.data;
            top5countries.value = data.map((item: CountryData) => item.country);
            populationData.value = data.map((item: CountryData) => item.population);

            for (let i = data.length - 1; i >= 0; i--) {
                const population: number = populationData.value[i];
                yAxisData.value.push({
                    value: top5countries.value[i]
                });
                seriesData.value.push({
                    value: populationData.value[i],
                    itemStyle: { color: '#621708' },
                    label: {
                        show: true,
                        position: 'inside',
                        formatter: function () {
                            return population.toLocaleString()
                        }
                    }
                });
            }

            renderBarChart();
        })
}

const renderBarChart = () => {
    const chartDom = document.getElementById("chart");
    if (!myChart) {
        myChart = echarts.init(chartDom);
    }
    option && myChart!.setOption(option);
}

const updateBarChart = () => {
    const apiUrl = 'https://harmonyoz.azurewebsites.net/findPopulationByCountry';
    axios
        .get(apiUrl, {
            params: {
                country: selectedCountry.value
            }
        })
        .then(response => {
            const data = response.data;
            const countryData = yAxisData.value
            const countryValue = seriesData.value
            if (countryData.length > 5) {
                countryData.splice(0, 1);
                countryValue.splice(0, 1);
            }

            if (selectedCountryIndex.value != -1) {
                countryValue[selectedCountryIndex.value].itemStyle = {
                    color: '#621708',
                };
            }

            let countryIndex = top5countries.value.indexOf(selectedCountry.value);

            if (lastCountryIndex.value != -1) {
                countryValue[lastCountryIndex.value].itemStyle = {
                    color: '#621708',
                };
            }

            if (countryIndex != -1) {
                if (countryIndex == 0) {
                    lastCountryIndex.value = 4
                } else if (countryIndex == 1) {
                    lastCountryIndex.value = 3
                } else if (countryIndex == 2) {
                    lastCountryIndex.value = 2
                } else if (countryIndex == 3) {
                    lastCountryIndex.value = 1
                } else if (countryIndex == 4) {
                    lastCountryIndex.value = 0
                }
                countryValue[lastCountryIndex.value].itemStyle = {
                    color: '#ee9b0b',
                };
            } else {
                selectedCountryIndex.value = -1;
                countryData.unshift(selectedCountry.value);
                countryValue.unshift({
                    name: selectedCountry.value,
                    value: data,
                    itemStyle: {
                        color: '#ee9b0b',
                    },
                    label: {
                        show: true,
                        position: 'right',
                        formatter: function () {
                            return data.toLocaleString()
                        }
                    }
                });
            }
            renderBarChart();
        })

}

</script>

<template>
    <div class="max-w-7xl mx-auto px-6 md:px-12 xl:px-6 mt-10">
        <section style="text-align: -webkit-center;">
            <div class="mr-auto place-self-center lg:col-span-7 text-center flex flex-col items-center justify-center">

                <!-- Adding the image in the same position as the original website -->
                <img class="w-full h-80 object-cover mx-auto" src="../assets/4883782.jpg" alt="">

                <h1 class="max-w-2xl mb-4 text-4xl font-bold tracking-tight leading-none lg:mt-5">
                    Settlement
                </h1>
                <p class="w-5/6 mx-auto mb-6 lg:mb-8 md:text-lg lg:text-xl text-justify">
                    Cultural diversity refers to the presence of various cultural groups and their unique characteristics
                    within a society. Acknowledging diverse perspectives promotes social integration, combats discrimination
                    and prejudice, and ultimately leads to a more inclusive and harmonious society.
                </p>
            </div>
            <div class="space-y-6 justify-between  md:flex md:gap-6 md:space-y-0 lg:gap-12 lg:items-center bg-white p-12">
                <div class="lg:w-1/2">
                    <div class="space-y-4" style="max-width: 450px;">
                        <p class="text-3xl">Did you know?</p>
                        <h3 class="text-left">The largest migrant population in Australia comes from England. <br>
                            Curious about other countries?</h3>
                        <div class="pt-4 flex">
                            <el-select-v2 class="form-item" v-model="selectedCountry" :options="option3"
                                placeholder="Select a country to compare" filterable size="large"
                                @change="updateBarChart" />
                        </div>
                    </div>
                </div>
                <div class="lg:w-1/2">
                    <div id="chart" style="min-width: 320px; max-width: 600px; height: 400px;"></div>
                </div>
            </div>
            <div class="h-18"></div>
            <div class="bg-white py-5 my-10">
                <Quessionnaire></Quessionnaire>
            </div>
        </section>
    </div>
</template>

<style scoped>
.form-item {
    width: 256px;
    text-align: left;
    -webkit-text-fill-color: black;
    border-width: 2px;
    border-radius: 6px;
    border-color: black;
    /* font-weight: bold; */
}

.form-item2 {
    width: 256px;
    text-align: left;
}
</style>
