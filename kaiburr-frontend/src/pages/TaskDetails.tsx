import React from 'react';
import { Drawer, List } from 'antd';
import type { Task } from '../types/task';
import dayjs from 'dayjs';

export default function TaskDetails({ task, onClose }: { task: Task | null, onClose: () => void }) {
  return (
    <Drawer title={task?.name} open={!!task} onClose={onClose} width={720}>
      {task && (
        <>
          <p><b>ID:</b> {task.id}</p>
          <p><b>Owner:</b> {task.owner}</p>
          <p><b>Command:</b> <code>{task.command}</code></p>

          <h3>Executions</h3>
          <List
            dataSource={task.taskExecutions ?? []}
            renderItem={item => (
              <List.Item>
                <div style={{ width: '100%' }}>
                  <div><b>Start:</b> {dayjs(item.startTime).format('YYYY-MM-DD HH:mm:ss')}</div>
                  <div><b>End:</b> {dayjs(item.endTime).format('YYYY-MM-DD HH:mm:ss')}</div>
                  <pre style={{ whiteSpace: 'pre-wrap' }}>{item.output}</pre>
                </div>
              </List.Item>
            )}
            locale={{ emptyText: 'No executions yet' }}
          />
        </>
      )}
    </Drawer>
  );
}
