<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from 'axios';
import { useRouter, useRoute } from 'vue-router';
import "leaflet/dist/leaflet.css";
// import { LMap, LTileLayer } from "@vue-leaflet/vue-leaflet";
import L from "leaflet";
import marker from '../assets/school.svg';
import marker2 from '../assets/playground.svg';
import { Icon } from 'leaflet'
const myIcon = new Icon({
    iconUrl: marker,
    iconSize: [32, 32]
})
const myIcon2 = new Icon({
    iconUrl: marker2,
    iconSize: [32, 32]
})

import * as echarts from 'echarts/core';
import {
    DatasetComponent,
    TitleComponent,
    TooltipComponent,
    GridComponent,
    TransformComponent
} from 'echarts/components';
import { LineChart } from 'echarts/charts';
import { UniversalTransition } from 'echarts/features';
import { CanvasRenderer } from 'echarts/renderers';

echarts.use([
    DatasetComponent,
    TitleComponent,
    TooltipComponent,
    GridComponent,
    TransformComponent,
    LineChart,
    CanvasRenderer,
    UniversalTransition
]);

let melGeojson: any = null;
const yourCountry = ref('');
const yourOcc = ref('');
const suburb1 = ref('');
const suburb2 = ref('');
const suburb3 = ref('');
const suburb4 = ref('');
const suburb5 = ref('');
const people1 = ref(0);
const people2 = ref(0);
const people3 = ref(0);
const people4 = ref(0);
const people5 = ref(0);
const populationDictionary: { [key: number]: number } = {};
const caseCountDictionary: { [key: number]: number } = {};
const playground = ref<any[]>([]);
const priceRange = ref()
const selectedAge = ref();
const eduSector = ref();

onMounted(async () => {
    const route = useRoute();
    const query = route.query
    if (query.priceRange) {
        priceRange.value = query.priceRange
    } else {
        priceRange.value = ""
    }
    if (query.selectedScType && query.selectedAge) {
        eduSector.value = query.selectedScType
        selectedAge.value = query.selectedAge
        await getPlayground();
    }
    if (query.mapSelectedCountry && query.selectedOccupation) {
        yourCountry.value = query.mapSelectedCountry as string;
        getPopByCountry(yourCountry.value, priceRange.value);
        yourOcc.value = query.selectedOccupation as string;
        getOccCountBySub(query.selectedOccupation, query.mapSelectedCountry)
        if (!melGeojson) {
            melGeojson = await getGeoJson();
            for (let i = 0; i < melGeojson.features.length; i++) {
                melGeojson.features[i].properties.population = populationDictionary[melGeojson.features[i].properties.SA2_CODE_2021]
                melGeojson.features[i].properties.caseCount = caseCountDictionary[melGeojson.features[i].properties.SA2_CODE_2021]
            }
            await renderMap();
        }
        await getOccForecast(yourOcc.value);
    }
});

const getGeoJson = async () => {
    const response = await fetch("https://pub-156d8808b1b14202890ee5b13d384224.r2.dev/melbourne.geojson", {
        headers: {
            "Accept-Encoding": "gzip, deflate",
        }
    })
    const vicGeojson = await response.json();
    return vicGeojson;
};

const getOccForecast = (occupation: string) => {
    const apiUrl = 'https://harmonyoz.azurewebsites.net/getOccForecast';
    axios
        .get(apiUrl, {
            params: {
                occupation: occupation
            }
        })
        .then(response => {
            renderLine(response.data);
        })
}

const renderLine = (data: any) => {
    const chartDom = document.getElementById("chart");
    const myChart2 = echarts.init(chartDom);
    myChart2.setOption({
        title: {
            text: 'Job Forecast for ' + yourOcc.value + " in Melbourne",
        },
        xAxis: {
            type: 'category',
            data: data.map((item: any) => { console.log("data"); return item.year }),
            name: "Year"
        },
        yAxis: {
            type: 'value',
            name: 'No. of jobs'
        },
        series: [{
            type: 'line',
            data: data.map((item: any) => item.job_count),
        }],

    })
}

const renderMap = () => {

    var map = L.map('map', {

    }).setView([-37.8, 145], 13);

    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);

    function getColor(d: any) {
        return d > 5000 ? '#800026' :
            d > 2500 ? '#BD0026' :
                d > 1000 ? '#E31A1C' :
                    d > 500 ? '#FC4E2A' :
                        d > 250 ? '#FD8D3C' :
                            d > 100 ? '#FEB24C' :
                                d > 50 ? '#FED976' :
                                    d > 0 ? '#FFEDA0' :
                                        '';
    }

    function style(feature: any) {
        return {
            fillColor: getColor(feature.properties.population),
            weight: 2,
            opacity: 1,
            color: 'white',
            dashArray: '3',
            fillOpacity: 0.7,

        };
    }

    var markerLayer = L.layerGroup().addTo(map);
    var markerLayer2 = L.layerGroup().addTo(map);

    function onEachFeature(feature: any, layer: any) {
        const popupContent = `
    <strong>Suburb:</strong> ${feature.properties.name}<br>
    <strong>Population:</strong> ${feature.properties.population}<br>
    <strong>No. of rental houses available in your budget:</strong> ${feature.properties.caseCount}
    `;

        // on hover event
        layer.on({
            mousemove: function () {
                this.bindPopup(popupContent).openPopup();
            },
            mouseout: function () {
                this.closePopup();
            }
        });

        if (eduSector.value === 'Government') {
            layer.on('click', function (e: any) {
                const apiUrl = 'https://harmonyoz.azurewebsites.net/getSchoolLoc';
                axios
                    .get(apiUrl, {
                        params: {
                            schoolType: selectedAge.value,
                            educationSector: eduSector.value,
                            x: e.latlng.lng,
                            y: e.latlng.lat
                        }
                    })
                    .then(response => {
                        const data = response.data;
                        markerLayer.clearLayers();
                        data.forEach(function (item: any) {
                            var latlng = L.latLng(item.y, item.x);
                            var marker = L.marker(latlng, { icon: myIcon }).addTo(markerLayer);
                            marker.bindPopup(`name:${item.name}, phone:${item.phone}, postcode:${item.postcode}`).openPopup();
                        });
                    })
            });
        }
    }

    if (eduSector.value === 'Independent') {
        const apiUrl = 'https://harmonyoz.azurewebsites.net/getScGeo';
        axios
            .get(apiUrl, {
                params: {
                    schoolType: selectedAge.value,
                    educationSector: eduSector.value
                }
            })
            .then(response => {
                const data = response.data;
                data.forEach(function (item: any) {
                    var latlng = L.latLng(item.y, item.x);
                    var marker = L.marker(latlng, { icon: myIcon }).addTo(markerLayer);
                    marker.bindPopup(`name:${item.name}, phone:${item.phone}, postcode:${item.postcode}`).openPopup();
                });
            })
    }

    if (playground.value) {
        playground.value.forEach(function (item: any) {
            var latlng = L.latLng(item.x, item.y);
            var marker = L.marker(latlng, { icon: myIcon2 }).addTo(markerLayer2);
            marker.bindPopup(item.name).openPopup();
        })
    }

    L.geoJson(melGeojson, { style: style, onEachFeature: onEachFeature }).addTo(map);

    /*Setup*/
    // var map = L.map("mapid").setView([55.67, 12.57], 7);
    L.tileLayer(
        "https://stamen-tiles.a.ssl.fastly.net/terrain/{z}/{x}/{y}.jpg"
    ).addTo(map);

    /*Legend specific*/
    var legend = new L.Control({ position: "bottomright" });

    legend.onAdd = function () {
        var div = L.DomUtil.create("div", "legend");
        div.innerHTML += "<h4>Legend</h4>";
        div.innerHTML += '<i><img src="../assets/school.svg" alt=""></i><span>School</span><br>';
        div.innerHTML += '<i class="icon" style="background-image: url("../assets/playground.svg");background-repeat: no-repeat;"></i><span>Playground</span><br>';



        return div;
    };

    legend.addTo(map);


}

const getOccCountBySub = (selectedOccupation: any, mapSelectedCountry: any) => {
    const apiUrl = 'https://harmonyoz.azurewebsites.net/getOccCountBySub';
    axios
        .get(apiUrl, {
            params: {
                occupation: selectedOccupation,
                country: mapSelectedCountry
            }
        })
        .then(response => {
            for (let i = 0; i < response.data.length; i++) {
                if (i == 0) {
                    suburb1.value = response.data[i].suburb
                    people1.value = response.data[i].people
                } else if (i == 1) {
                    suburb2.value = response.data[i].suburb
                    people2.value = response.data[i].people
                } else if (i == 2) {
                    suburb3.value = response.data[i].suburb
                    people3.value = response.data[i].people
                } else if (i == 3) {
                    suburb4.value = response.data[i].suburb
                    people4.value = response.data[i].people
                } else if (i == 4) {
                    suburb5.value = response.data[i].suburb
                    people5.value = response.data[i].people
                }
            }
        })
}

const getPopByCountry = (mapSelectedCountry: any, priceRange: any) => {
    const apiUrl = 'https://harmonyoz.azurewebsites.net/getMapDataByCountry';
    axios
        .get(apiUrl, {
            params: {
                country: mapSelectedCountry + '_Tot',
                priceRange: priceRange
            }
        })
        .then(response => {
            const data = response.data;
            data.forEach((item: any) => {
                const subCode = item.subCode;
                const population = item.population;
                const caseCount = item.caseCount;
                populationDictionary[subCode] = population;
                caseCountDictionary[subCode] = caseCount;
            });
        })
}

const getPlayground = () => {
    const apiUrl = 'https://harmonyoz.azurewebsites.net/getPlayground';
    axios
        .get(apiUrl)
        .then(response => {
            const data = response.data;
            playground.value = data;
        })
}

const router = useRouter();
const goBack = () => {
    router.go(-1);
}

</script>

<template>
    <div class="max-w-7xl mx-auto px-6 md:px-12 xl:px-6 bg-white py-10">
        <div class="Recommend">
            <div class="mt-6" style="text-align: -webkit-center;">
                <h1 class="max-w-2xl mb-5 text-4xl font-bold tracking-tight leading-none">
                    Our Recommendations
                </h1>
                <p class="mt-4 text-left text-center">
                    Based on the selected options, we recommend the top 5 suburbs in Melbourne
                    for you and your family.
                </p>
            </div>

            <div class="space-y-6 mt-6 mb-1 md:flex md:gap-6 md:space-y-0 lg:gap-12">
                <div class="lg:w-1/2">
                    <div id="chart" style="max-width: 600px; height: 400px;"></div>
                </div>
                <div class="lg:w-1/2">
                    <h1 class="max-w-2xl text-slate-700 font-bold text-2xl tracking-tight leading-none">
                        5 best suburbs we recommend</h1>
                    <div class="overflow-x-auto">
                        <table class="table">
                            <!-- head -->
                            <thead>
                                <tr>
                                    <th></th>
                                    <th></th>
                                    <!-- <th>Comment(Among the people who come from {{ yourCountry }})</th> -->
                                </tr>
                            </thead>
                            <tbody>
                                <!-- row 1 -->
                                <tr class="hover">
                                    <th>
                                        <div class="rating rating-md">
                                            <input type="radio" name="rating-8" class="mask mask-star-2 bg-orange-400"
                                                disabled />
                                            <input type="radio" name="rating-8" class="mask mask-star-2 bg-orange-400"
                                                disabled />
                                            <input type="radio" name="rating-8" class="mask mask-star-2 bg-orange-400"
                                                disabled />
                                            <input type="radio" name="rating-8" class="mask mask-star-2 bg-orange-400"
                                                disabled />
                                            <input type="radio" name="rating-8" class="mask mask-star-2 bg-orange-400"
                                                checked disabled />
                                        </div>
                                    </th>
                                    <td>{{ suburb1 }}</td>
                                    <!-- <td>There are {{ people1 }} individuals working in the area as {{ yourOcc }}.</td> -->
                                </tr>
                                <!-- row 2 -->
                                <tr class="hover">
                                    <th>
                                        <div class="rating rating-md">
                                            <input type="radio" name="rating-7" class="mask mask-star-2 bg-orange-400"
                                                disabled />
                                            <input type="radio" name="rating-7" class="mask mask-star-2 bg-orange-400"
                                                disabled />
                                            <input type="radio" name="rating-7" class="mask mask-star-2 bg-orange-400"
                                                disabled />
                                            <input type="radio" name="rating-7" class="mask mask-star-2 bg-orange-400"
                                                checked disabled />
                                            <input type="radio" name="rating-7" class="mask mask-star-2 bg-orange-400"
                                                disabled />
                                        </div>
                                    </th>
                                    <td>{{ suburb2 }}</td>
                                    <!-- <td>There are {{ people2 }} individuals working in the area as {{ yourOcc }}.</td> -->
                                </tr>
                                <!-- row 3 -->
                                <tr class="hover">
                                    <th>
                                        <div class="rating rating-md">
                                            <input type="radio" name="rating-6" class="mask mask-star-2 bg-orange-400"
                                                disabled />
                                            <input type="radio" name="rating-6" class="mask mask-star-2 bg-orange-400"
                                                disabled />
                                            <input type="radio" name="rating-6" class="mask mask-star-2 bg-orange-400"
                                                disabled />
                                            <input type="radio" name="rating-6" class="mask mask-star-2 bg-orange-400"
                                                checked disabled />
                                            <input type="radio" name="rating-6" class="mask mask-star-2 bg-orange-400"
                                                disabled />
                                        </div>
                                    </th>
                                    <td>{{ suburb3 }}</td>
                                    <!-- <td>There are {{ people3 }} individuals working in the area as {{ yourOcc }}.</td> -->
                                </tr>
                                <tr class="hover">
                                    <th>
                                        <div class="rating rating-md">
                                            <input type="radio" name="rating-5" class="mask mask-star-2 bg-orange-400"
                                                disabled />
                                            <input type="radio" name="rating-5" class="mask mask-star-2 bg-orange-400"
                                                disabled />
                                            <input type="radio" name="rating-5" class="mask mask-star-2 bg-orange-400"
                                                checked disabled />
                                            <input type="radio" name="rating-5" class="mask mask-star-2 bg-orange-400"
                                                disabled />
                                            <input type="radio" name="rating-5" class="mask mask-star-2 bg-orange-400"
                                                disabled />
                                        </div>
                                    </th>
                                    <td>{{ suburb4 }}</td>
                                    <!-- <td>There are {{ people4 }} individuals working in the area as {{ yourOcc }}.</td> -->
                                </tr>
                                <tr class="hover">
                                    <th>
                                        <div class="rating rating-md">
                                            <input type="radio" name="rating-3" class="mask mask-star-2 bg-orange-400"
                                                disabled />
                                            <input type="radio" name="rating-3" class="mask mask-star-2 bg-orange-400"
                                                disabled />
                                            <input type="radio" name="rating-3" class="mask mask-star-2 bg-orange-400"
                                                checked disabled />
                                            <input type="radio" name="rating-3" class="mask mask-star-2 bg-orange-400"
                                                disabled />
                                            <input type="radio" name="rating-3" class="mask mask-star-2 bg-orange-400"
                                                disabled />
                                        </div>
                                    </th>
                                    <td>{{ suburb5 }}</td>
                                    <!-- <td>There are {{ people5 }} individuals working in the area as {{ yourOcc }}.</td> -->
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div>
            <h1 class="text-center pb-10">
                <p class="text-2xl font-bold">Cultural distribution in Melbourne</p>
                <p>click on the map to view school locations</p>
            </h1>
            <div id="map" class="w-full" style="height: 500px;"></div>
        </div>
        <div class="relative h-10 w-full mt-6 ">
            <button class="absolute inset-y-0 left-0 btn btn-wide btn-warning" @click="goBack">Go Back</button>
            <router-link to="/jobready">
                <button class="absolute inset-y-0 right-0 btn btn-wide btn-success">Explore courses</button>
            </router-link>
        </div>
    </div>
</template>
<style>
/*Setup*/
html,
body {
    padding: 0;
    margin: 0;
}

html,
body,
#mapid {
    height: 100%;
    width: 100%;
}

/*Legend specific*/
.legend {
    padding: 6px 8px;
    font: 14px Arial, Helvetica, sans-serif;
    background: white;
    background: rgba(255, 255, 255, 0.8);
    /*box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);*/
    /*border-radius: 5px;*/
    line-height: 24px;
    color: #555;
}

.legend h4 {
    text-align: center;
    font-size: 16px;
    margin: 2px 12px 8px;
    color: #777;
}

.legend span {
    position: relative;
    bottom: 3px;
}

.legend i {
    width: 18px;
    height: 18px;
    float: left;
    margin: 0 8px 0 0;
    opacity: 0.7;
}

.legend i.icon {
    background-size: 18px;
    background-color: rgba(255, 255, 255, 1);
}
</style>