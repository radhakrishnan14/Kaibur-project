import React, { useEffect } from 'react';
import { Modal, Form, Input, message } from 'antd';
import { useCreateOrUpdateTask } from '../hooks/useTasks';
import type { Task } from '../types/task';

type Props = {
  visible: boolean;
  onClose: () => void;
  editTask?: Task | null;
};

export default function TaskForm({ visible, onClose, editTask }: Props) {
  const [form] = Form.useForm();
  const createMutation = useCreateOrUpdateTask();

  useEffect(() => {
    form.resetFields();
    if (editTask) form.setFieldsValue(editTask);
  }, [visible, editTask, form]);

  const onFinish = async (values: Task) => {
    try {
      await createMutation.mutateAsync(values);
      message.success('Saved');
      onClose();
    } catch (e: any) {
      message.error(e.message ?? 'Save failed');
    }
  };

  return (
    <Modal title={editTask ? 'Edit Task' : 'Create Task'} open={visible} onCancel={onClose} onOk={() => form.submit()}>
      <Form form={form} layout="vertical" onFinish={onFinish}>
        <Form.Item name="id" label="ID" rules={[{ required: true }]}>
          <Input />
        </Form.Item>
        <Form.Item name="name" label="Name" rules={[{ required: true }]}>
          <Input />
        </Form.Item>
        <Form.Item name="owner" label="Owner">
          <Input />
        </Form.Item>
        <Form.Item
          name="command"
          label="Command"
          rules={[
            { required: true },
            { pattern: /^[a-zA-Z0-9 _.-]+( .*)?$/, message: 'Command contains invalid characters.' }
          ]}
        >
          <Input />
        </Form.Item>
      </Form>
    </Modal>
  );
}
