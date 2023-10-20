<template>
  <div class="cards max-w-7xl mx-auto px-6 md:px-12 xl:px-6 py-10">
    <div class="mr-auto place-self-center lg:col-span-7 text-center flex flex-col items-center justify-center">
      <img class="w-full h-80 object-cover mx-auto" src="../assets/landscape.jpg" alt="">
      <h1 class="max-w-2xl mb-4 lg:mt-5 tracking-tight leading-none">
        <span class="font-bold text-4xl py-10">Challenges</span> <br>
        <span class="text-2xl">Faced by refugee population in Australia</span>
      </h1>
      <p class="w-5/6 mx-auto mb-6 lg:mb-8 md:text-lg lg:text-xl text-justify">
        When embarking on a journey in a new environment, it becomes crucial to gain a comprehensive
        understanding of the potential challenges ahead. This awareness empowers individuals to proactively take
        precautions and devise strategies to effectively manage and minimize any adverse consequences that may
        arise.
      </p>
      <p>Try tapping cards below to discover new worlds</p>
    </div><!-- Header Content omitted for brevity... -->

    <!-- Cards Rows -->
    <div class="cards-row grid grid-cols-3 gap-8 p-12 bg-white">
      
      <!-- Card 1 -->
      <!-- Card 1 -->
      <div class="card-container">
        <div :class="['card', toggle1 ? 'flipped' : '']" @click="toggle1 = !toggle1">
          <div class="card-front">
            <img src="../assets/government_new.svg" alt="image" class="w-full rounded-t-xl" />
          </div>
          <div class="card-back">
            <h3 class="card-title">
              Job-seeking Hurdles in Australia
            </h3>
            <p>
              In Australia, refugees face challenges while seeking jobs mainly because their foreign
              qualifications aren't acknowledged.
              <span class="highlighted">88.1%</span> of people rely on government subsidies even after six
              months.
            </p>
          </div>
        </div>
      </div>


      <!-- Card 2 -->
      <div class="card-container">
        <div :class="['card', toggle2 ? 'flipped' : '']" @click="toggle2 = !toggle2">
          <div class="card-front">
            <img src="../assets/Language.jpg" alt="image" class="w-full rounded-t-xl" />
          </div>
          <div class="card-back">
            <h3 class="card-title">
              Language Barriers in Australia
            </h3>
            <p>
              Effective communication is pivotal for assimilation in Australia. With English proficiency being
              crucial, <span class="highlighted">56%</span> face significant language barriers.
            </p>
          </div>
        </div>
      </div>

      <!-- Card 3 -->
      <div class="card-container">
        <div :class="['card', toggle3 ? 'flipped' : '']" @click="toggle3 = !toggle3">
          <div class="card-front">
            <img src="../assets/Housing.jpg" alt="image" class="w-full rounded-t-xl" />
          </div>
          <div class="card-back">
            <h3 class="card-title">
              Housing Challenges for Refugees
            </h3>
            <p>
              Despite governmental aid, choosing the right settlement area is complex due to cultural values
              and family obligations. <span class="highlighted">28.1%</span> face problems finding a home.
            </p>
          </div>
        </div>
      </div>

      <!-- Card 4 -->
      <div class="card-container">
        <div :class="['card', toggle4 ? 'flipped' : '']" @click="toggle4 = !toggle4">
          <div class="card-front">
            <img src="../assets/Cultura_new.svg" alt="image" class="w-full rounded-t-xl" />
          </div>
          <div class="card-back">
            <h3 class="card-title">
              Cultural Integration and Isolation
            </h3>
            <p>
              About <span class="highlighted">45.3%</span> of refugees experience feelings of cultural
              isolation, making it hard for them to integrate into the broader Australian society.
            </p>
          </div>
        </div>
      </div>

      <!-- Card 5 -->
      <div class="card-container">
        <div :class="['card', toggle5 ? 'flipped' : '']" @click="toggle5 = !toggle5">
          <div class="card-front">
            <img src="../assets/Education_new.svg" alt="image" class="w-full rounded-t-xl" />
          </div>
          <div class="card-back">
            <h3 class="card-title">
              Educational Adjustment for Refugee Families
            </h3>
            <p>
              About <span class="highlighted">39.7%</span> of refugee families find navigating the educational
              system for their children challenging in a new country.
            </p>
          </div>
        </div>
      </div>

      <!-- Card 6 -->
      <div class="card-container">
        <div :class="['card', toggle6 ? 'flipped' : '']" @click="toggle6 = !toggle6">
          <div class="card-front">
            <img src="../assets/Safety.svg" alt="image" class="w-full rounded-t-xl" />
          </div>
          <div class="card-back">
            <h3 class="card-title">
              Safety Concerns among Refugees
            </h3>
            <p>
              Safety is a paramount concern for approximately <span class="highlighted">50.5%</span> of
              refugees, who often are unaware of safer neighborhoods versus areas with higher crime rates.
            </p>
          </div>
        </div>
      </div>
    </div>
    <router-link to="/diversity">
      <div class="text-center mt-10">
        <button class="btn" r>Find out about the communities established in Australia</button>
      </div>
    </router-link>

  </div>
</template>
<style scoped>
.cards-row {
  margin-top: 30px;
}

.card-container {
  perspective: 1000px;
  height: 400px;
  transition: transform 0.3s;
}

.card-container:hover {
  transform: translateY(-10px);
}

.card {
  position: relative;
  background: #f7f9fc;
  padding: 20px;
  border-radius: 15px;
  transition: transform 0.7s;
  transform-style: preserve-3d;
  height: 100%;
}

.card-front,
.card-back {
  backface-visibility: hidden;
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.card-back {
  background-image: linear-gradient(-90deg, #AEDFF7, #D1AFFF);
  transform: rotateY(180deg);
  padding: 20px;
}

.card.flipped {
  transform: rotateY(180deg);
}

.card img {
  width: 100%;
  border-radius: 15px 15px 0 0;
}

.card-title {
  color: #333;
  font-size: 1.4rem;
  font-weight: 600;
  margin-bottom: 15px;
  text-align: center;
  margin: 15px auto;
}

.card-back p {
  color: #555555;
  font-size: 1.1rem;
  line-height: 1.5;
  text-align: center;
}

.highlighted {
  color: #f56a79;
  font-weight: bold;
}
</style>



<script setup lang="ts">
import { ref } from "vue";

const toggle1 = ref(false);
const toggle2 = ref(false);
const toggle3 = ref(false);
const toggle4 = ref(false);
const toggle5 = ref(false);
const toggle6 = ref(false);
</script>
