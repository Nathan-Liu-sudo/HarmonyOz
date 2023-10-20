import { createRouter, createWebHistory } from 'vue-router';
import Home from './components/Home.vue';
import Challenges from './components/Challenges.vue';
import Diversity from './components/Diversity.vue';
import SettlementSol from './components/SettlementSol.vue';
import JobReady from './components/JobReadyPage/JobReady.vue';
// import Opportunity from './components/Opportunity.vue';

const router = createRouter({
  history: createWebHistory(),
  routes: [
    // 通配符路由，将所有未匹配的路由重定向到根路由
    { path: '/:pathMatch(.*)*', redirect: '/' },
    { path: '/', component: Home },
    { path: '/challenges', component: Challenges },
    { path: '/diversity', component: Diversity },
    { path: '/jobready', component: JobReady },
    { path: '/settlesol', component: SettlementSol },
    // { path: '/opportunity', component: Opportunity },
  ],
  scrollBehavior() {
    window.scrollTo(0, 0);
  }
});

export default router;