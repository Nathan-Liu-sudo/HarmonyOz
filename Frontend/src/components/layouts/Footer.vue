<template>
    <!-- <footer class="footer footer-center bg-neutral text-white p-4 text-base-content">
        <div>
            <p>Copyright © 2023 - All right reserved by team TP11</p>
        </div>
    </footer> -->
    <footer class="footer p-10 bg-neutral text-white text-base-content">
        <aside>
            <div class="footer-logo flex">
                <img src="../../assets/logo.svg">
                <p class="my-auto">Creative Commons Attribution 4.0 license.
                </p>
            </div>
        </aside>
        <nav>
            <header class="footer-title">Services</header>
            <a class="link link-hover"><router-link to="/">Home</router-link></a>
            <a class="link link-hover"><router-link to="/challenges">Challenges</router-link></a>
            <a class="link link-hover"><router-link to="/diversity">Settlement</router-link></a>
            <a class="link link-hover"><router-link to="/jobready">Job-ready</router-link></a>
        </nav>
        <nav>
            <header class="footer-title">Company</header>
            <!-- <a class="link link-hover"><router-link to="/team">Our Team</router-link></a> -->
            <a>TP11</a>
        </nav>
    </footer>
</template>

<style scoped>
.footer-logo {
    height: 50px;
    margin: auto;
    justify-content: center;
}
</style>