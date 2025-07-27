<script lang="ts" setup>
import { reactive, ref } from "vue";

// Tipos
interface Task {
    id: number;
    title: string;
    assignee: string;
    icon: string;
    comments: number;
    attachments: number;
    priority: "high" | "medium" | "low"; // Novo campo de prioridade
}

interface Column {
    id: number;
    title: string;
    tasks: Task[];
    isDraggingOver: boolean;
    dragCounter: number;
}

// Dados reativos
const columns = reactive<Column[]>([
    {
        id: 1,
        title: "A Fazer",
        tasks: [
            {
                id: 1,
                title: "Implementar login",
                assignee: "João",
                icon: "check_circle",
                comments: 3,
                attachments: 2,
                priority: "high",
            },
            {
                id: 2,
                title: "Criar página inicial",
                assignee: "Maria",
                icon: "check_circle",
                comments: 0,
                attachments: 0,
                priority: "medium",
            },
            {
                id: 3,
                title: "Configurar banco de dados",
                assignee: "Ana",
                icon: "bug_report",
                comments: 2,
                attachments: 1,
                priority: "low",
            },
        ],
        isDraggingOver: false,
        dragCounter: 0,
    },
    {
        id: 2,
        title: "Em Progresso",
        tasks: [
            {
                id: 4,
                title: "Testar API",
                assignee: "Pedro",
                icon: "check_circle",
                comments: 5,
                attachments: 3,
                priority: "high",
            },
            {
                id: 5,
                title: "Revisar código",
                assignee: "Lucas",
                icon: "bug_report",
                comments: 1,
                attachments: 0,
                priority: "medium",
            },
        ],
        isDraggingOver: false,
        dragCounter: 0,
    },
    {
        id: 3,
        title: "Concluído",
        tasks: [
            {
                id: 6,
                title: "Configurar servidor",
                assignee: "Sofia",
                icon: "check_circle",
                comments: 0,
                attachments: 1,
                priority: "low",
            },
        ],
        isDraggingOver: false,
        dragCounter: 0,
    },
]);

const draggedTaskId = ref<number | null>(null);
const sourceColumn = ref<Column | null>(null);
const openMenuTaskId = ref<number | null>(null);
let nextTaskId = 7; // Para IDs únicos

// Função para obter o ícone de prioridade
const getPriorityIcon = (priority: Task["priority"]): string => {
    switch (priority) {
        case "high":
            return "ri-arrow-up-line";
        case "medium":
            return "ri-arrow-right-line";
        case "low":
            return "ri-arrow-down-line";
        default:
            return "ri-arrow-down-line";
    }
};

// Função para obter a cor do ícone de prioridade
const getPriorityColor = (priority: Task["priority"]): string => {
    switch (priority) {
        case "high":
            return "error"; // Vermelho
        case "medium":
            return "warning"; // Amarelo
        case "low":
            return "success"; // Verde
        default:
            return "success";
    }
};

// Funções
const toggleMenu = (taskId: number) => {
    openMenuTaskId.value = openMenuTaskId.value === taskId ? null : taskId;
};

const closeMenuIfNotThis = (taskId: number) => {
    if (openMenuTaskId.value !== null && openMenuTaskId.value !== taskId) {
        openMenuTaskId.value = null;
    }
};

const openTask = (task: Task) => {
    console.log(`Abrindo tarefa KAN-${task.id}: ${task.title}`);
    openMenuTaskId.value = null;
};

const copyTaskLink = (task: Task) => {
    const link = `http://example.com/task/KAN-${task.id}`;
    navigator.clipboard
        .writeText(link)
        .then(() => {
            alert(`Link copiado: ${link}`);
        })
        .catch(() => {
            alert("Erro ao copiar o link");
        });
    openMenuTaskId.value = null;
};

const duplicateTask = (task: Task, column: Column) => {
    const newTask: Task = {
        id: nextTaskId++,
        title: task.title,
        assignee: task.assignee,
        icon: task.icon,
        comments: task.comments,
        attachments: task.attachments,
        priority: task.priority,
    };
    column.tasks.push(newTask);
    openMenuTaskId.value = null;
};

const onDragStart = (task: Task, column: Column) => {
    draggedTaskId.value = task.id;
    sourceColumn.value = column;
    openMenuTaskId.value = null; // Fechar menu ao arrastar
};

const onDragEnd = () => {
    draggedTaskId.value = null;
    sourceColumn.value = null;
    columns.forEach((column) => {
        column.isDraggingOver = false;
        column.dragCounter = 0;
    });
};

const onDragOver = (event: DragEvent) => {
    event.preventDefault();
};

const onDragEnter = (column: Column) => {
    column.dragCounter++;
    column.isDraggingOver = true;
};

const onDragLeave = (column: Column) => {
    column.dragCounter--;
    if (column.dragCounter === 0) {
        column.isDraggingOver = false;
    }
};

const onDrop = (targetColumn: Column) => {
    if (draggedTaskId.value !== null && sourceColumn.value) {
        const task = sourceColumn.value.tasks.find((t) => t.id === draggedTaskId.value);
        if (task) {
            sourceColumn.value.tasks.splice(
                sourceColumn.value.tasks.findIndex((t) => t.id === draggedTaskId.value),
                1
            );
            targetColumn.tasks.push(task);
        }
        columns.forEach((column) => {
            column.isDraggingOver = false;
            column.dragCounter = 0;
        });
        draggedTaskId.value = null;
        sourceColumn.value = null;
    }
};
</script>

<template>
    <div class="kanban-board">
        <div
            v-for="column in columns"
            :key="column.id"
            class="kanban-column"
            :class="{ 'drag-over': column.isDraggingOver }"
            @dragover.prevent="onDragOver"
            @dragenter.prevent="onDragEnter(column)"
            @dragleave.prevent="onDragLeave(column)"
            @drop="onDrop(column)"
        >
            <div class="column-title">{{ column.title }}</div>
            <v-card
                v-for="task in column.tasks"
                :key="task.id"
                class="task-card"
                :class="{ dragging: draggedTaskId === task.id }"
                draggable="true"
                @dragstart="onDragStart(task, column)"
                @dragend="onDragEnd"
                @mouseenter="closeMenuIfNotThis(task.id)"
                elevation="2"
                :style="{ border: '2px solid #dfe1e6' }"
            >
                <v-card-text>
                    <v-row align="center" no-gutters>
                        <v-col cols="auto">
                            <!-- <v-icon
                                :icon="task.icon"
                                size="small"
                                color="primary"
                                class="mr-1"
                            ></v-icon> -->
                            <span class="task-id">KAN-{{ task.id }}</span>
                        </v-col>
                        <v-spacer></v-spacer>
                        <v-col cols="auto">
                            <v-menu>
                                <template v-slot:activator="{ props }">
                                    <v-btn
                                        icon="ri-more-2-line"
                                        size="small"
                                        variant="text"
                                        color="grey-darken-1"
                                        v-bind="props"
                                    ></v-btn>
                                </template>
                                <v-list>
                                    <v-list-item @click="openTask(task)">
                                        <v-list-item-title>Abrir</v-list-item-title>
                                    </v-list-item>
                                    <v-list-item @click="copyTaskLink(task)">
                                        <v-list-item-title
                                            >Copiar o link</v-list-item-title
                                        >
                                    </v-list-item>
                                    <v-list-item @click="duplicateTask(task, column)">
                                        <v-list-item-title>Duplicar</v-list-item-title>
                                    </v-list-item>
                                </v-list>
                            </v-menu>
                        </v-col>
                    </v-row>
                    <div class="task-title mt-2">{{ task.title }}</div>
                    <v-row align="center" no-gutters class="mt-2">
                        <v-col cols="auto">
                            <span class="mr-3">
                                <v-icon
                                    icon="ri-chat-3-line"
                                    size="small"
                                    color="grey-darken-1"
                                    class="mr-1"
                                ></v-icon>
                                {{ task.comments }}
                            </span>
                            <span class="mr-3">
                                <v-icon
                                    icon="ri-attachment-line"
                                    size="small"
                                    color="grey-darken-1"
                                    class="mr-1"
                                ></v-icon>
                                {{ task.attachments }}
                            </span>
                            <span>
                                <v-icon
                                    :icon="getPriorityIcon(task.priority)"
                                    :color="getPriorityColor(task.priority)"
                                    size="small"
                                    class="mr-1"
                                ></v-icon>
                            </span>
                        </v-col>
                        <v-spacer></v-spacer>
                        <v-col cols="auto">
                            <span class="task-avatar">{{ task.assignee[0] }}</span>
                        </v-col>
                    </v-row>
                </v-card-text>
            </v-card>
        </div>
    </div>
</template>

<style scoped>
.kanban-board {
    display: flex;
    padding: 20px;
    background-color: #f0f2f5;
    gap: 20px;
    min-block-size: 100vh;
}

.kanban-column {
    padding: 15px;
    border-radius: 8px;
    background-color: rgba(255, 255, 255, 32.2%);
    box-shadow: 0 2px 4px rgba(0, 0, 0, 10%);
    inline-size: 300px;
    min-block-size: 500px;
}

.column-title {
    color: #333;
    font-size: 1.2em;
    font-weight: bold;
    margin-block-end: 16px;
}

.task-card {
    margin-block-end: 10px; /* Espaço entre cartões */
}

.task-card.dragging {
    opacity: 0.5;
}

.drag-over {
    border: 2px dashed #0052cc; /* Cor azul do Jira */
    background-color: #e6f3ff;
}

.task-avatar {
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 50%;
    background-color: #0052cc; /* Azul do Jira */
    block-size: 24px;
    color: white;
    font-size: 0.8em;
    font-weight: bold;
    inline-size: 24px;
}

.task-id {
    color: #0052cc; /* Azul do Jira */
    font-size: 0.9em;
    font-weight: bold;
}

.task-title {
    color: #172b4d; /* Cor de texto do Jira */
    font-size: 1em;
}
</style>
