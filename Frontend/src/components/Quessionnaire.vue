<script setup lang="ts">
import axios from 'axios';
import { ref, onUnmounted, onMounted } from 'vue';
import { useRouter } from 'vue-router';

const selectedOccupation = ref(),
    mapSelectedCountry = ref(),
    educationSector = ref(),
    selectedAge = ref(),
    priceRange = ref(),
    hasChildren = ref<boolean>(),
    nextDisabled = ref<boolean>(true);

const option6 = ref<any[]>([]);
const option7 = [
    { label: 'Primary(Age 6-12)', value: 'Pri' },
    { label: 'Secondary(Age 12-16)', value: 'Sec' }
];
const option8 = [
    { label: 'Public', value: 'Government' },
    { label: 'Private', value: 'Independent' }
];
const option9 = ref<any[]>([]);
const option10 = [
    { label: '$1-$74', value: '$1-$74' },
    { label: '$100-$149', value: '$100-$149' },
    { label: '$150-$199', value: '$150-$199' },
    { label: '$200-$224', value: '$200-$224' },
    { label: '$225-$274', value: '$225-$274' },
    { label: '$275-$349', value: '$275-$349' },
    { label: '$350-$449', value: '$350-$449' },
    { label: '$450-$549', value: '$450-$549' },
    { label: '$550-$649', value: '$550-$649' },
    { label: '$650-$749', value: '$650-$749' }
];

const mapCountries = ref<string[]>([]);
const getMapCountries = () => {
    const apiUrl = 'https://harmonyoz.azurewebsites.net/getMapCountries';
    axios
        .get(apiUrl)
        .then(response => {
            mapCountries.value = response.data
            option9.value = Array.from({ length: mapCountries.value.length }).map((_, idx) => ({
                value: mapCountries.value[idx],
                label: mapCountries.value[idx].replace(/_/g, ' '),
            }))
        })
}

const getAllOccupations = () => {
    const apiUrl = 'https://harmonyoz.azurewebsites.net/getAllOccupations';
    axios
        .get(apiUrl)
        .then(response => {
            option6.value = Array.from({ length: response.data.length }).map((_, idx) => ({
                value: response.data[idx],
                label: response.data[idx],
            }))
        })
}
let currentSlide = ref(1);
// const totalSlides = 3;
let intervalId: ReturnType<typeof setInterval> | null = null;

onMounted(() => {
    getAllOccupations();
    getMapCountries();
})

onUnmounted(() => {
    if (intervalId) clearInterval(intervalId);
});

const scrollCarousel = (targetImageNumber: any) => {
    let carouselElement = document.getElementById("offSol");
    let carouselWidth = carouselElement?.clientWidth;

    let targetImage = targetImageNumber - 1;
    let targetXPixel = 0;

    if (carouselWidth !== undefined) {
        targetXPixel = (carouselWidth * targetImage) + 1;
    }

    carouselElement?.scrollTo(targetXPixel, 0);
};


const router = useRouter();

const submitForm = () => {
    currentSlide.value = 1;
    if (mapSelectedCountry.value == undefined) {
        alert('Please select a country');
        return;
    }
    if (selectedOccupation.value === undefined) {
        alert('Please select an occupation');
        return;
    }
    if (educationSector.value === undefined && selectedAge.value) {
        alert('Please select an education sector');
        return;
    }
    if (selectedAge.value === undefined && educationSector.value) {
        alert('Please select an age');
        return;
    }
    router.push({
        path: `/settlesol`,
        query: {
            // selectedIndustry: selectedIndustry.value,
            selectedOccupation: selectedOccupation.value,
            mapSelectedCountry: mapSelectedCountry.value,
            selectedScType: educationSector.value,
            selectedAge: selectedAge.value,
            priceRange: priceRange.value
        }
    })
}


</script>

<template>
    <div class="w-full">
        <div id="offSol" class="carousel w-full">

            <div id="item1" class="carousel-item w-full">
                <div class="w-3/6 mx-10 my-auto">
                    <div class="space-y-4 mt-10 mb-10" style="max-width: 450px;">
                        <h3 class="text-5xl text-slate-400/50 text-left">Finding a suitable settlement?</h3>
                        <h3 class="text-5xl text-blue-400 text-left font-bold">We can help you with that! ➡</h3>
                    </div>

                </div>
                <div class="w-3/6 mx-10 my-auto">
                    <div class="space-y-4 mt-10 mb-10" style="max-width: 450px;">
                        <h3 class="text-2xl text-justify">Get started by telling us about yourself so we can help you get
                            started on your journey!</h3>
                    </div>

                </div>
            </div>
            <div id="item2" class="carousel-item w-full">
                <div class="w-3/6 mx-10 my-auto">
                    <p class="text-3xl">Where are you from?<span style="color:red;">*</span></p>
                    <br>
                    <el-select-v2 class="form-item w-5/6" v-model="mapSelectedCountry" :options="option9"
                        placeholder="Select a country" filterable size="large" :onChange="() => nextDisabled = false" />
                </div>
                <div class="w-3/6 mx-10">
                    <div class="flex justify-center"><img class="max-h-16 object-contain" src="../assets/idea.jpg" alt="">
                        <p class="text-2xl font-bold my-auto ml-5">Did you know</p>
                    </div>
                    <div>
                        <p class="text-justify">Considering the country of origin is crucial when settling in a new country
                            due to its influence
                            on an individual's cultural, social, and psychological adaptation. Research reveals that
                            acknowledging one's cultural background fosters a sense of identity and facilitates the
                            integration process. </p>
                    </div>
                </div>
            </div>

            <div id="item3" class="carousel-item w-full">
                <div class="w-3/6 mx-10 my-auto">
                    <p class="text-3xl">What was your past occupation?<span style="color:red;">*</span></p>
                    <br>
                    <el-select-v2 class="form-item w-5/6" v-model="selectedOccupation" :options="option6"
                        placeholder="Select an occupation" filterable size="large" :onChange="() => nextDisabled = false" />
                </div>
                <div class="w-3/6 mx-10">
                    <div class="flex justify-center"><img class="max-h-16 object-contain" src="../assets/idea.jpg" alt="">
                        <p class="text-2xl font-bold my-auto ml-5">Did you know</p>
                    </div>
                    <div>
                        <p class="text-justify">Considering your occupation is essential when finding a settlement in a new
                            country as it significantly impacts your economic stability, career prospects, and overall
                            well-being. Research highlights the correlation between job opportunities and successful
                            integration, emphasizing the importance of matching skills with the host country's labor market
                            demands. </p>
                    </div>
                </div>
            </div>

            <div id="item4" class="carousel-item w-full">
                <div class="w-3/6 mx-10 my-auto">
                    <p class="text-3xl">What is your weekly budget for housing?<span style="color:red;">*</span></p>
                    <br>
                    <el-select-v2 class="form-item w-5/6" v-model="priceRange" :options="option10"
                        placeholder="Select the price range" filterable size="large"
                        :onChange="() => nextDisabled = false" />
                </div>
                <div class="w-3/6 mx-10">
                    <div class="flex justify-center"><img class="max-h-16 object-contain" src="../assets/idea.jpg" alt="">
                        <p class="text-2xl font-bold my-auto ml-5">Did you know</p>
                    </div>
                    <div>
                        <p class="text-justify">Research highlights the impact of affordable housing on overall well-being
                            and socioeconomic advancement, particularly for immigrants and refugees. High rent prices can
                            strain financial resources, leading to increased economic vulnerability and hindered access to
                            essential resources.

                        </p>
                    </div>
                </div>

            </div>
            <div id="item5" class="carousel-item w-full">
                <div class="w-3/6 mx-10 my-auto">
                    <p class="text-3xl">Do you have any children?<span style="color:red;">*</span></p>
                    <br>
                    <input type="radio" name="radio-1" class="radio"
                        :onChange="() => { hasChildren = true; nextDisabled = false }" /> Yes
                    <input type="radio" name="radio-1" class="radio"
                        :onChange="() => { hasChildren = false; nextDisabled = false }" /> No
                </div>
                <div class="w-3/6 mx-10">
                    <div class="flex justify-center"><img class="max-h-16 object-contain" src="../assets/idea.jpg" alt="">
                        <p class="text-2xl font-bold my-auto ml-5">Did you know</p>
                    </div>
                    <div>
                        <p class="text-justify">In Australia, public schools are typically zoned according to specific
                            geographical areas, and students living within a school's designated zone are given priority for
                            enrollment. Families residing outside
                            a particular school's zone may find it challenging to secure enrollment, although some schools
                            may accept students from outside the zone depending on capacity and other factors. </p>
                    </div>
                </div>
            </div>

            <div id="item6" class="carousel-item w-full">
                <div class="w-3/6 mx-10 my-auto">
                    <p class="text-3xl">How old are your kids?<span style="color:red;">*</span></p>
                    <br>
                    <el-select-v2 class="form-item w-5/6" v-model="selectedAge" :options="option7"
                        placeholder="Select your kids' age" filterable size="large"
                        :onChange="() => nextDisabled = false" />
                </div>
                <div class="w-3/6 mx-10">
                    <div class="flex justify-center"><img class="max-h-16 object-contain" src="../assets/idea.jpg" alt="">
                        <p class="text-2xl font-bold my-auto ml-5">Did you know</p>
                    </div>
                    <div>
                        <p class="text-justify">Research indicates that younger children often adapt more quickly to new
                            surroundings, languages, and cultures compared to older children. For school-age children,
                            selecting settlements with reputable educational institutions, extracurricular activities, and a
                            supportive community aids in their academic and social development. </p>
                    </div>
                </div>
            </div>
            <div id="item7" class="carousel-item w-full">
                <div class="w-3/6 mx-10 my-auto">
                    <p class="text-3xl">What kind of educational institutes would you prefer?<span
                            style="color:red;">*</span></p>
                    <br>
                    <el-select-v2 class="form-item w-5/6" v-model="educationSector" :options="option8"
                        placeholder="Select the school type" filterable size="large"
                        :onChange="() => nextDisabled = false" />
                </div>
                <div class="w-3/6 mx-10">
                    <div class="flex justify-center"><img class="max-h-16 object-contain" src="../assets/idea.jpg" alt="">
                        <p class="text-2xl font-bold my-auto ml-5">Did you know</p>
                    </div>
                    <div>
                        <p class="text-justify">The affordability of public versus private education can vary significantly
                            depending on the country, region, and local economic conditions. In general, public schools are
                            funded by the government and thus typically offer tuition-free education, making them more
                            accessible for families with limited financial resources. Private schools, on the other hand,
                            often require tuition fees and additional expenses for various activities, making them
                            relatively more expensive. </p>
                    </div>
                </div>
            </div>
            <div id="item8" class="carousel-item w-full">
                <div class="w-fill mx-10 my-auto">
                    <p class="text-3xl">We are ready with your personalised recommendations.</p>
                    <br>
                    <p class="text-3xl">Head on over to view our recommendations, or retake the quiz to explore other
                        options.</p>

                </div>
            </div>
        </div>

        <div class="flex justify-center w-full py-2 gap-2">

            <button class="btn w-2/6" @click="() => { currentSlide--; scrollCarousel(currentSlide); }"
                :class="{ visible: currentSlide != 3, hidden: currentSlide === 1 || currentSlide === 8 }">Prev</button>
            <button class="btn w-2/6" :disabled="nextDisabled"
                @click="() => { currentSlide += (currentSlide == 5) ? (hasChildren ? 1 : 3) : 1; scrollCarousel(currentSlide); }"
                :class="{ visible: currentSlide > 1, hidden: currentSlide === 1 || currentSlide === 8 }">Next</button>
            <button class="btn w-2/6" @click="() => { currentSlide++; scrollCarousel(currentSlide); }"
                :class="{ visible: currentSlide === 1, hidden: currentSlide != 1 }">Start Quiz</button>

            <button class="btn w-2/6" @click="() => { currentSlide = 1; scrollCarousel(currentSlide); }"
                :class="{ visible: currentSlide == 8, hidden: currentSlide != 8 }">Retake
                quiz</button>
            <button class="btn w-2/6" @click="submitForm"
                :class="{ visible: currentSlide == 8, hidden: currentSlide != 8 }">View
                results</button>
        </div>
    </div>
</template>