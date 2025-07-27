import type { App } from 'vue'
import { createRouter, createWebHistory } from 'vue-router'
import { routes } from './routes'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

export default function (app: App) {
  app.use(router)
}

router.beforeEach((to, from, next) => {
    const token: string | null = localStorage.getItem('token');

    if (to.name === 'error') {
        next();
    }
    
    if (to.name != 'login' && token === null) {
        next({ name: 'login' });
    } else {
        next();
    }
});

export { router }
