<script setup lang="ts">

// import { ref } from 'vue'
// import add from './JobReady'

// const count = ref(0)
// add(1, 2)

</script>

<template>
  <div class="cards max-w-7xl mx-auto px-6 md:px-12 xl:px-6 py-10">
    <div class="mr-auto place-self-center lg:col-span-7 text-center flex flex-col items-center justify-center">
      <img class=" w-full h-80 object-cover mx-auto" src="../../assets/9464349_4149931.svg" alt="">
      <h1 class="max-w-2xl mb-4 tracking-tight leading-none pt-3">
        <span class="font-bold text-4xl">Job seekers</span> <br>
      </h1>
      <p class="w-4/6 mx-auto mb-6 lg:mb-8 md:text-lg lg:text-xl text-center">
        Having trouble finding work? <br>
        Are your past qualifications not recognized? <br>
        Don't worry, we have you covered
      </p>
    </div>

    <div class="cards max-w-7xl mx-auto px-6 md:px-12 xl:px-6 bg-white py-10 mb-10">
      <CourseSection />
    </div>

    <router-link to="/"><button class="btn w-full">Head to the home page</button></router-link>

    <!-- <div class="cards max-w-7xl mx-auto px-6 md:px-12 xl:px-6 bg-white py-10 mb-10">
    <JobSection />
  </div> -->
  </div>
</template>