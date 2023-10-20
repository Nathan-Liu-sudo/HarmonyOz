<script setup lang="ts">
import { onMounted, ref, watch } from 'vue'

const displayMenu = ref(false);
const displayMenu2 = ref(true);
const windowWidth = ref(window.innerWidth)

onMounted(() => {

    if (windowWidth.value < 768) {
        displayMenu.value = true;
        displayMenu2.value = false;
    }
})

// watch the window size
watch(windowWidth, (newWidth) => {
    if (newWidth < 768) {
        displayMenu.value = true;
        displayMenu2.value = false;
    } else {
        displayMenu.value = false;
        displayMenu2.value = true;
    }
})

// update window width when the window is resized
window.addEventListener('resize', () => {
    windowWidth.value = window.innerWidth;
})

const lastItemId = ref('');
const higtLight = (id: string) => {   
    document.getElementById(lastItemId.value)?.classList.remove("active");
    document.getElementById(lastItemId.value)?.classList.remove("text-orange-300");
    document.getElementById(id)?.classList.add("active");
    document.getElementById(id)?.classList.add("text-orange-300");
    lastItemId.value = id;
}

</script>

<template>
    <div class="navbar bg-neutral text-white shadow-xl">
        <div class="flex-1">
            <!-- <a class="btn btn-ghost normal-case text-xl">Bridging Culture</a> -->
            <div class="relative z-20 flex w-full justify-between md:px-0 lg:w-max">
                <a href="/" aria-label="logo" class="flex items-center space-x-2">
                    <div class="w-6 rounded-full">
                        <img src="/src/assets/logo.svg" />
                    </div>
                    <span class="text-1xl font-bold">HarmonyOz</span>
                </a>
            </div>
        </div>
        <div class="flex-none">
            <ul class="menu menu-horizontal px-1">
                <li @click="higtLight('item1')" id="item1" class="menu-item" v-if="displayMenu2"><router-link to="/">Home</router-link></li>
                <li @click="higtLight('item2')" id="item2" class="menu-item" v-if="displayMenu2"><router-link to="/challenges">Challenges</router-link></li>
                <li @click="higtLight('item3')" id="item3" class="menu-item" v-if="displayMenu2"><router-link to="/diversity">Settlement</router-link></li>
                <li @click="higtLight('item5')" id="item5" class="menu-item" v-if="displayMenu2"><router-link to="/jobready">Job-Ready</router-link></li>

                <li v-if="displayMenu">
                    <details>
                        <summary>
                            Menu
                        </summary>
                        <ul class="p-2 bg-neutral">
                            <li @click="higtLight('item1')" id="item1" class="menu-item"><router-link to="/">Home</router-link></li>
                            <li @click="higtLight('item2')" id="item2" class="menu-item"><router-link to="/challenges">Challenges</router-link></li>
                            <li @click="higtLight('item3')" id="item3" class="menu-item"><router-link to="/diversity">Settlement</router-link></li>
                            <li @click="higtLight('item5')" id="item5" class="menu-item"><router-link to="/jobready">Job-Ready</router-link></li>
                        </ul>
                    </details>
                </li>
            </ul>
        </div>
    </div>
</template>

<style>
.navbar {
    z-index: 999;
}

.swap-rotate:hover svg {
    fill: orange
}

.menu .menu-item:hover a {
    color: orange;
}

.menu li.active > a {
  background-color: transparent;
  color: inherit;
}

</style>