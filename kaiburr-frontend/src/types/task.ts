export type TaskExecution = {
  startTime: string; // ISO timestamp from backend
  endTime: string;
  output: string;
};

export type Task = {
  id: string;
  name: string;
  owner?: string;
  command: string;
  taskExecutions?: TaskExecution[];
};
