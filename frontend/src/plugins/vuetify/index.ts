import type { App } from "vue";

import { createI18n, useI18n } from "vue-i18n";
import { createVuetify } from "vuetify";
import { VBtn } from "vuetify/components/VBtn";
import { createVueI18nAdapter } from "vuetify/locale/adapters/vue-i18n";
import defaults from "./defaults";
import { icons } from "./icons";
import { themes } from "./theme";
// Styles

import { message_en, message_pt_BR } from "@/@core/i18n/messages";
import "@core/scss/template/libs/vuetify/index.scss";
import "vuetify/styles";

const i18n = createI18n({
    legacy: false, // Vuetify does not support the legacy mode of vue-i18n
    locale: "pt_BR",
    fallbackLocale: "en",
    messages: {
        pt_BR: message_pt_BR,
        en: message_en,
    },
});

export default function (app: App) {
    const vuetify = createVuetify({
        aliases: {
            IconBtn: VBtn,
        },
        defaults,
        icons,
        locale: {
            adapter: createVueI18nAdapter({ i18n, useI18n }),
        },
        theme: {
            defaultTheme: "light",
            themes,
        },
    });
    app.use(i18n);
    app.use(vuetify);
}
