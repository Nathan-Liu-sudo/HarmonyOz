<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue';

let currentSlide = ref(1);
const totalSlides = 3;
let intervalId: ReturnType<typeof setInterval> | null = null;

onMounted(() => {
    const autoPlay = () => {
        if (currentSlide.value >= totalSlides) {
            currentSlide.value = 1;
        } else {
            currentSlide.value++;
        }
        scrollCarousel(currentSlide.value);
    }

    intervalId = setInterval(autoPlay, 3000);
});

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

const scrollToInfo = () => {
    const el = document.querySelector("#infoSolutions");
    if (el) {
        el.scrollIntoView({ behavior: 'smooth' });
    }
}
</script>

<template>
    <section>
        <div class="h-screen w-100 flex">
            <div class="w-5/12 h-5/6 items-center flex flex-wrap content-center font-bold text-center px-8">
                <div class="w-full">
                    <p class="text-5xl font-serif">Your guide to calling Australia home</p>
                </div>
                <div class="w-full pt-10">
                    <router-link to="/challenges">
                        <button class="btn btn-neutral" style="background-color: #918d55; color: white;">Learn more</button>
                    </router-link>
                </div>
            </div>
            <div class="w-7/12 h-4/6 flex content-end">
                <img src="../assets/28008245_7287550.svg" />
            </div>
            <div @click=scrollToInfo() class="scrolldown static"></div>
        </div>
    </section>
    <section>
        <div id="infoSolutions" class="cards mx-16 py-1 my-20 md:px-12 xl:px-20 bg-white rounded-3xl">
            <div class="card my-20">
                <div class="space-y-6 md:flex md:gap-6 md:space-y-0 lg:gap-12 lg:items-center">
                    <div class="w-1/2">
                        <h1 class="mb-4 text-5xl font-bold tracking-tight leading-none text-left">
                            From communities, to children's education, <br> We have it covered
                        </h1>
                        <p class="mb-6 lg:mb-8 md:text-lg text-left lg:text-xl lg:mt-10 text-justify">
                            We understand the challenges that refugees face when searching for a new place to call home. Our
                            mission
                            is to be your guiding light, offering support and resources to ease your journey to a better
                            life. We
                            believe that everyone deserves a safe and welcoming environment, and we are here to help you
                            find it.

                        </p>
                    </div>
                    <div class="w-1/2">
                        <div id="offSol" class="carousel w-full">
                            <div id="item1" class="carousel-item w-full">
                                <div class="w-full ">
                                    <div class="w-5/6 flex mx-auto">
                                        <img class="w-5/6 mx-auto" src="../assets/20286058_6194197.svg" />
                                    </div>
                                    <div class="w-5/6 text-center mx-auto my-5">
                                        <p class="text-4xl py-2">Personalised recommendations</p>
                                        <p class="text-justify">Our dedicated team is committed to providing personalized
                                            assistance tailored to your unique needs. Try out our quiz to quickly get
                                            started on your journey.</p>
                                    </div>
                                </div>
                            </div>
                            <div id="item2" class="carousel-item w-full">
                                <div class="w-full ">
                                    <div class="w-5/6 flex mx-auto">
                                        <img class="h-2/6 mx-auto" src="../assets/7732593_5220.svg" />
                                    </div>
                                    <div class="w-5/6 text-center mx-auto my-5">
                                        <p class="text-4xl py-2">Community Integration</p>
                                        <p class="text-justify">We believe that building connections within your new
                                            community is essential for a successful transition. Our programs and resources
                                            are designed to help you integrate into your new surroundings, fostering a sense
                                            of belonging.

                                        </p>
                                    </div>
                                </div>
                            </div>
                            <div id="item3" class="carousel-item w-full">
                                <div class="w-full ">
                                    <div class="w-5/6 flex mx-auto">
                                        <img class="h-full mx-auto" src="../assets/9354274_4151544.svg" />
                                    </div>
                                    <div class="w-5/6 text-center mx-auto my-5">
                                        <p class="text-4xl py-2">Educational Opportunities</p>
                                        <p class="text-justify">Education is the key to a brighter future. We provide
                                            information on local schools, and educational resources your children excel
                                            academically.

                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- <div class="flex justify-center w-full py-2 gap-2">
                            <button @click="() => { currentSlide = 1; scrollCarousel(1); }" class="btn"></button>
                            <button @click="() => { currentSlide = 2; scrollCarousel(2); }" class="btn"></button>
                            <button @click="() => { currentSlide = 3; scrollCarousel(3); }" class="btn"></button>
                        </div> -->
                    </div>
                </div>
            </div>
        </div>
        <div class="cards mx-16 py-1 my-20 md:px-12 xl:px-20 bg-white rounded-3xl">
            <div class="card my-20">
                <div class="space-y-6 md:flex md:gap-6 md:space-y-0 lg:gap-12 lg:items-center">
                    <div class="w-1/2">
                        <div><img
                                src="../assets/landscape.jpg" />
                        </div>
                    </div>
                    <div class="w-1/2 align-center">
                        <h1 class="mb-4 text-5xl font-bold tracking-tight leading-none text-left">
                            Grass may not be greener on the other side
                        </h1>
                        <p class="mb-6 lg:mb-8 md:text-lg text-left lg:text-xl lg:mt-10 text-justify">
                            Embarking on a journey to a new country is an incredible leap of faith, filled with
                            opportunities and challenges alike. At HarmonyOz, we want to prepare you for the hurdles you may
                            encounter as you begin your life in a foreign land. Understanding these challenges is the first
                            step towards overcoming them, and we're here to support you every step of the way.
                        </p>
                        <router-link to="/challenges">
                            <button class="btn w-full"> Start your journey</button>
                        </router-link>
                    </div>
                </div>
            </div>
        </div>
    </section>
</template>

<style scoped>
button.btn {
    background-color: #E74C3C;
    color: white;
}
img.background {
    object-fit: cover;
}
.scrolldown::before {
    animation: bounce 1s ease infinite;
    bottom: 0;
    color: #918d55;
    content: '╲╱';
    font-size: 2rem;
    height: 4rem;
    left: 50%;
    letter-spacing: -1px;
    line-height: 4rem;
    margin-left: -3rem;
    opacity: 0.8;
    position: absolute;
    text-align: center;
    width: 6rem;
    cursor: pointer;
}

@keyframes bounce {
    50% {
        transform: translateY(-50%);
    }
}
</style>
