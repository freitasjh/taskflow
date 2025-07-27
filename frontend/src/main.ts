import { createApp } from "vue";

import App from "@/App.vue";
import { registerPlugins } from "@core/utils/plugins";

// Styles
import api from "@/plugins/axios/axios";
import "@core/scss/template/index.scss";
import "@layouts/styles/index.scss";
import "sweetalert2/dist/sweetalert2.min.css";
import { LoadingPlugin } from "vue-loading-overlay";
import "vue-loading-overlay/dist/css/index.css";
import VueSweetAlert from "vue-sweetalert2";
import VuetifyMask from "vuetify-mask";

// Create vue app
const app = createApp(App);
app.config.globalProperties.$axios = api;

// Register plugins
registerPlugins(app);

app.use(VueSweetAlert);
app.use(VuetifyMask);
app.use(LoadingPlugin);

// Mount vue app
app.mount("#app");
