export const routes = [
    { path: "/", redirect: "/dashboard" },
    {
        path: "/",
        component: () => import("@/layouts/default.vue"),
        children: [
            {
                path: "dashboard",
                component: () => import("@/pages/dashboard.vue"),
            },
            {
                path: "employee",
                component: () =>
                    import(
                        "@/administrator/employee/page/layout/employee-layout.vue"
                    ),
            },
            {
                path: "team",
                component: () =>
                    import("@/administrator/team/page/layout/team-layout.vue"),
            },
            {
                path: "workflow",
                component: () =>
                    import(
                        "@/administrator/workflow/page/layout/workflow-layout.vue"
                    ),
            },
            {
                path: "kanban-board",
                component: () =>
                    import("@/kanban/layout/kanban-board-layout.vue"),
            },
            {
                path: "project-status",
                component: () =>
                    import(
                        "@/administrator/project/layout/page/project-status-layout.vue"
                    ),
            },
            {
                path: "project",
                component: () =>
                    import(
                        "@/administrator/project/layout/page/project-layout.vue"
                    ),
            },
            {
                path: "icons",
                component: () => import("@/pages/icons.vue"),
            },
            {
                path: "cards",
                component: () => import("@/pages/cards.vue"),
            },
            {
                path: "tables",
                component: () => import("@/pages/tables.vue"),
            },
            {
                path: "form-layouts",
                component: () => import("@/pages/form-layouts.vue"),
            },
        ],
    },
    {
        path: "/",
        component: () => import("@/layouts/blank.vue"),
        children: [
            {
                path: "login",
                component: () => import("@/pages/login.vue"),
                name: "login",
            },
            {
                path: "register",
                component: () => import("@/pages/register.vue"),
            },
            {
                path: "/:pathMatch(.*)*",
                component: () => import("@/pages/[...error].vue"),
            },
        ],
    },
];
