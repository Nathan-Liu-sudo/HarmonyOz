<script setup lang="ts">
import { ref, onMounted } from 'vue'

const passwordVerified = ref(false)
const password = ref()
const hasEnteredPassword = ref(localStorage.getItem('hasEnteredPassword') === 'true')

const checkPassword = () => {
  if (password.value === 'tp11_app') {
    passwordVerified.value = true;
    hasEnteredPassword.value = true;

    // 将验证状态和标志存储在本地存储中
    localStorage.setItem('passwordVerified', 'true');
    localStorage.setItem('hasEnteredPassword', 'true');
  } else {
    alert('Password Incorrect');
  }
}

onMounted(() => {
  // 检查本地存储中的标志来确定用户是否已经输入过密码
  if (hasEnteredPassword.value) {
    passwordVerified.value = true;
  }
})
</script>

<template>
  <div v-if="!passwordVerified" class="passBox">
    <div class="search">
      <input v-model="password" type="password" placeholder="Please input password" />
      <button type="submit" @click="checkPassword">Submit</button>
    </div>
  </div>
  <div v-else class="min-h-screen flex flex-col">
    <Header />
    <div class="flex-grow" style="background-color: #ffefe9;">
      <router-view v-slot="{ Component }">
        <keep-alive :include="['Diversity']">
          <component :is="Component" />
        </keep-alive>
      </router-view>
      <Topbtn />
    </div>
    <Footer />
  </div>
</template>

<style scoped>
.passBox {
  margin-top: 300px;
  text-align: center;
}

.search {
  display: inline-block;
  position: relative;
}

.search input[type="password"] {
  width: 300px;
  padding: 10px;
  border: none;
  border-radius: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.search button[type="submit"] {
  background-color: #4e99e9;
  border: none;
  color: #fff;
  cursor: pointer;
  padding: 10px 20px;
  border-radius: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  position: absolute;
  top: 0;
  right: 0;
  transition: .9s ease;
}

.search button[type="submit"]:hover {
  transform: scale(1.1);
  color: rgb(255, 255, 255);
  background-color: blue;
}
</style>