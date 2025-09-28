import React, { useState } from 'react';
import { Table, Button, Space, Modal, Input, message } from 'antd';
import { useTasksQuery, useDeleteTask, useExecuteTask, useSearchTasks } from '../hooks/useTasks';
import type { Task } from '../types/task';
import TaskForm from './TaskForm';
import TaskDetails from './TaskDetails';

export default function TaskList() {
  const { data: tasks = [], isLoading } = useTasksQuery();
  const [selectedTask, setSelectedTask] = useState<Task | null>(null);
  const [createVisible, setCreateVisible] = useState(false);
  const [search, setSearch] = useState('');
  const searchQuery = useSearchTasks(search);
  const deleteMutation = useDeleteTask();
  const executeMutation = useExecuteTask();

  const onDelete = (id: string) => {
    Modal.confirm({
      title: 'Delete task?',
      onOk: async () => {
        try {
          await deleteMutation.mutateAsync(id);
          message.success('Deleted');
        } catch (e: any) {
          message.error(e.message || 'Delete failed');
        }
      }
    });
  };

  const onExecute = async (id: string) => {
    try {
      const res = await executeMutation.mutateAsync(id);
      Modal.info({
        title: 'Execution output',
        width: 700,
        content: <pre style={{ whiteSpace: 'pre-wrap' }}>{res?.output ?? '(no output)'}</pre>
      });
    } catch (e: any) {
      message.error(e.message ?? 'Execute failed');
    }
  };

  const dataSource = search ? searchQuery.data ?? [] : tasks;

  const columns = [
    { title: 'ID', dataIndex: 'id', key: 'id' },
    { title: 'Name', dataIndex: 'name', key: 'name' },
    { title: 'Owner', dataIndex: 'owner', key: 'owner' },
    { title: 'Command', dataIndex: 'command', key: 'command' },
    {
      title: 'Actions', key: 'actions',
      render: (_: any, record: Task) => (
        <Space>
          <Button onClick={() => setSelectedTask(record)}>View</Button>
          <Button onClick={() => setCreateVisible(true) /* pass record to form via props if editing */}>Edit</Button>
          <Button onClick={() => onExecute(record.id)}>Run</Button>
          <Button danger onClick={() => onDelete(record.id)}>Delete</Button>
        </Space>
      )
    }
  ];

  return (
    <div style={{ padding: 24 }}>
      <Space style={{ marginBottom: 12 }}>
        <Button type="primary" onClick={() => setCreateVisible(true)}>Create Task</Button>
        <Input.Search
          placeholder="Search by name"
          allowClear
          onSearch={(val) => setSearch(val)}
          style={{ width: 300 }}
        />
      </Space>

      <Table rowKey="id" dataSource={dataSource} columns={columns} loading={isLoading} />

      <TaskForm
        visible={createVisible}
        onClose={() => setCreateVisible(false)}
        // optional: pass editTask for editing
      />

      <TaskDetails task={selectedTask} onClose={() => setSelectedTask(null)} />
    </div>
  );
}
