<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png" />
    <HelloWorld msg="Welcome to the Bookstore Application" />
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from "@/components/Login.vue";

export default {
  name: "Home",
  components: {
    HelloWorld,
  },
};
</script>
