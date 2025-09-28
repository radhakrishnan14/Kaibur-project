// src/hooks/useTasks.ts
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import api from '../api/api';
import type { Task } from '../types/task';

/** Fetch all tasks */
export const useTasksQuery = () =>
  useQuery({
    queryKey: ['tasks'],
    queryFn: async (): Promise<Task[]> => {
      const res = await api.get('/tasks');
      return res.data as Task[];
    },
  });

/** Search tasks by name (enabled only when name provided) */
export const useSearchTasks = (name: string) =>
  useQuery({
    queryKey: ['tasks', 'search', name],
    queryFn: async (): Promise<Task[]> => {
      const res = await api.get('/tasks/search', { params: { name }});
      return res.data as Task[];
    },
    enabled: !!name,
  });

/** Create or update a task */
export const useCreateOrUpdateTask = () => {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (task: Task) => api.put('/tasks', task).then(r => r.data as Task),
    onSuccess: () => qc.invalidateQueries({ queryKey: ['tasks'] }),
  });
};

/** Delete task */
export const useDeleteTask = () => {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => api.delete(`/tasks/${id}`).then(() => {}),
    onSuccess: () => qc.invalidateQueries({ queryKey: ['tasks'] }),
  });
};

/** Execute task */
export const useExecuteTask = () => {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => api.put(`/tasks/${id}/execute`).then(r => r.data),
    onSuccess: () => qc.invalidateQueries({ queryKey: ['tasks'] }),
  });
};
