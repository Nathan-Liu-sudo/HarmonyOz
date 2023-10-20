<script setup lang="ts">
import axios from 'axios';
import { ref } from 'vue';

class CourseInfo {

  courseCode: string;
  courseName: string;
  courseLevel: string;
  courseDuration: number;
  courseFee: number;
  constructor(courseCode: string,
    courseName: string,
    courseLevel: string,
    courseDuration: number,
    courseFee: number) {
    this.courseCode = courseCode;
    this.courseName = courseName;
    this.courseLevel = courseLevel;
    this.courseDuration = courseDuration;
    this.courseFee = courseFee;

  }
}

const keyword = ref(""), maxFee = ref<number>(0), minFee = ref<number>(0),
  maxDuration = ref<number>(0), minDuration = ref<number>(0);

const selectedLevels = ref<string[]>([]);

const courseDataOg = ref<CourseInfo[]>([]);
const courseDataFiltered = ref<CourseInfo[]>([]);
let totalPages = 2;
let currentPage = 1;
const levels = ref<Set<string>>(new Set())
const itemsPerPage = 10; // Number of items per page
const tableData = ref<CourseInfo[]>([])

// const getAllJobs = () => {
//   const apiUrl = 'https://harmonyoz.azurewebsites.net/getCourseInfo';
//   axios
//     .get(apiUrl)
//     .then(response => {
//       courseDataOg.value = response.data;
//       courseDataFiltered.value = courseDataOg.value;
//       totalPages = Math.ceil(courseDataOg.value.length / itemsPerPage);

//       maxFee.value = courseDataOg.value.reduce((a, b) => a.courseFee > b.courseFee ? a : b).courseFee;
//       maxDuration.value = courseDataOg.value.reduce((a, b) => a.courseDuration > b.courseDuration ? a : b).courseDuration;
//       minFee.value = courseDataOg.value.reduce((a, b) => a.courseFee < b.courseFee ? a : b).courseFee;
//       minDuration.value = courseDataOg.value.reduce((a, b) => a.courseDuration < b.courseDuration ? a : b).courseDuration;

//       courseDataOg.value.forEach(item => levels.value.add(item.courseLevel))

//       tableData.value = paginatedData()



//     })
// }

const getAllCourses = () => {
  const apiUrl = 'https://harmonyoz.azurewebsites.net/getCourseInfo';
  axios
    .get(apiUrl)
    .then(response => {
      courseDataOg.value = response.data;
      courseDataFiltered.value = courseDataOg.value;
      totalPages = Math.ceil(courseDataOg.value.length / itemsPerPage);

      maxFee.value = courseDataOg.value.reduce((a, b) => a.courseFee > b.courseFee ? a : b).courseFee;
      maxDuration.value = courseDataOg.value.reduce((a, b) => a.courseDuration > b.courseDuration ? a : b).courseDuration;
      minFee.value = courseDataOg.value.reduce((a, b) => a.courseFee < b.courseFee ? a : b).courseFee;
      minDuration.value = courseDataOg.value.reduce((a, b) => a.courseDuration < b.courseDuration ? a : b).courseDuration;

      courseDataOg.value.forEach(item => levels.value.add(item.courseLevel))

      tableData.value = paginatedData()



    })
}
getAllCourses()

function submitForm() {
  courseDataFiltered.value = courseDataOg.value.filter(item => {


    return (item.courseFee <= maxFee.value)
      && (item.courseFee >= minFee.value)
      && (item.courseDuration >= minDuration.value)
      && (item.courseDuration <= maxDuration.value)
      && (item.courseName.toLowerCase().includes(keyword.value.toLowerCase()))
      && (!selectedLevels.value.length || (selectedLevels.value.includes(item.courseLevel)))
  })

  tableData.value = paginatedData()
  totalPages = Math.ceil(courseDataFiltered.value.length / itemsPerPage);

}


// Calculate the total number of pages
// Calculate the data to display on the current page
function paginatedData() {
  const startIndex = (currentPage - 1) * itemsPerPage;
  const endIndex = startIndex + itemsPerPage;
  return courseDataFiltered.value.slice(startIndex, endIndex);
};


function previousPage() {
  console.log("PreviousPage")
  if (currentPage > 1) {
    currentPage--;
    tableData.value = paginatedData();
    console.log(tableData);
  }

};
function nextPage() {
  console.log("nextPage")
  if (currentPage < totalPages) {
    currentPage++;
    tableData.value = paginatedData();
    console.log(tableData);
  }
};

</script>

<template>
  <div>
    <div class="flex">
      <div class="w-4/6 my-auto align-center px-16">
        <p>
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Ratione, fugiat praesentium nulla repellat eligendi
          illum
          id distinctio culpa aut voluptas veniam odio qui accusamus sapiente labore, inventore velit voluptatum at?
        </p>>

      </div>
      <div class="w-2/6 block">
        Lorem ipsum dolor sit amet consectetur adipisicing elit. Provident sapiente, illum, voluptates quam ut, facilis
        ipsa
        quisquam vel ipsum aspernatur nihil praesentium itaque consequuntur magnam voluptate esse laboriosam? Aut,
        molestias.
      </div>
    </div>
    <div class="collapse bg-base-200">
      <input type="checkbox" class="peer" />
      <div class="collapse-title text-center">
        Learn more
      </div>
      <div class="collapse-content bg-white">
        <div class="w-full mt-10">
          <p class="text-xl text-center">Courses that can help</p>
          <div class="form-control w-full">
            <label class="label">
              <span class="label-text">Filter course by keywords</span>
            </label>
            <input type="text" placeholder="Type here" class="input input-bordered w-full" v-model="keyword" />
            <div class="flex justify-start">
              <div class="w-3/6">
                <label class="label">
                  <span class="label-text">Enter minimum course fee</span>
                </label>
                <input type="number" min="0" oninput="validity.valid||(value='');" placeholder="Type here"
                  class="input input-bordered w-5/6" v-model="minFee" />
              </div>
              <div class="w-3/6">
                <label class="label">
                  <span class="label-text">Enter maximum course fee</span>
                </label>
                <input type="number" min="0" oninput="validity.valid||(value='');" placeholder="Type here"
                  class="input input-bordered w-5/6" v-model="maxFee" />
              </div>
            </div>
            <div class="flex justify-start">
              <div class="w-3/6">
                <label class="label">
                  <span class="label-text">Enter minimum course Duration (in weeks)</span>
                </label>
                <input type="number" min="0" oninput="validity.valid||(value='');" placeholder="Type here"
                  class="input input-bordered w-5/6" v-model="minDuration" />
              </div>
              <div class="w-3/6">
                <label class="label">
                  <span class="label-text">Enter maximum course Duration (in weeks)</span>
                </label>
                <input type="number" min="0" oninput="validity.valid||(value='');" placeholder="Type here"
                  class="input input-bordered w-5/6" v-model="maxDuration" />
              </div>
            </div>
            <div class="label label-text">Select course level
            </div>
            <div class="grid grid-cols-3 gap-4">
              <div v-for="level in levels">
                <label class="label cursor-pointer px-5 justify-start">
                  <input type="checkbox" class="checkbox" v-model="selectedLevels" :value="level" />
                  <span class="label-text px-5">{{ level }}</span>
                </label>
              </div>
            </div>

            <div class="text-right">
              <button class="btn mt-4" @click="submitForm">Search</button>
            </div>
          </div>
          <div class="form-control w-full">

            <!-- <input type="text" placeholder="Filter your course results..." class="input input-bordered w-full" ref="search" /> -->
          </div>
          <div class="overflow-x-auto">
            <table class="table table-striped">
              <!-- head -->
              <thead>
                <tr>
                  <td>Code</td>
                  <td>Name</td>
                  <td>Level</td>
                  <td>Duration ksdfgbnsjlidfngjklsfdnkjlgnfdjklgnk</td>
                  <td>Fee</td>
                </tr>
              </thead>
              <tbody>
                <tr v-for="course in tableData" :key="course.courseCode" class="hover">
                  <td>{{ course.courseCode }}</td>
                  <td>{{ course.courseName }}</td>
                  <td>{{ course.courseLevel }}</td>
                  <td>{{ course.courseDuration }}</td>
                  <td>{{ course.courseFee }}</td>
                </tr>
                <!-- row 1 -->
              </tbody>
            </table>
            <!-- Pagination controls -->
            <div class="pagination text-center join grid grid-cols-2 my-10">
              <button class="btn join-item btn-outline" @click="previousPage"
                :disabled="currentPage === 1">Previous</button>
              <button class="btn join-item btn-outline" @click="nextPage"
                :disabled="currentPage === totalPages">Next</button>
            </div>
            <div class="text-center">Page {{ currentPage }} of {{ totalPages }}</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style>
.btn {
  background-color: #E74C3C;
  color: white;
}

.btn:hover {
  background-color: white;
  color: black;
}
</style>