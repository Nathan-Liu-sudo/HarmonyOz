<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from 'vue';
const scrollPosition = ref(false);
const scrollToTop = () => {
    window.scrollTo({
        top: 0,
        behavior: 'smooth', 
    });
}

const handleScroll = () => {
    scrollPosition.value = window.scrollY > 200;
};

onMounted(() => {
    window.addEventListener('scroll', handleScroll);
});

onBeforeUnmount(() => {
    window.removeEventListener('scroll', handleScroll);
});

</script>

<template>
    <div class="arrow-container" @click="scrollToTop">
        <img v-if="scrollPosition" src='../../assets/topbtn.svg'>
    </div>
</template>

<style scoped>
.arrow-container {
    width: 30px;
    height: 30px;
    position: fixed;
    bottom: 20px;
    right: 30px;
    display: flex;
    align-items: center;
    justify-content: center;
    height: 20vh;
}

.arrow-container:hover {
    cursor: pointer;
}

</style>