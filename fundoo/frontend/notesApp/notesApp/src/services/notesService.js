import api from './api.js';

const BASE = '/api/notes';

export const getNotes = () =>
    api.get(BASE).then(r => r.data);

export const createNote = (data) =>
    api.post(BASE, data).then(r => r.data);

export const updateNote = (id, data) =>
    api.patch(`${BASE}/${id}`, data).then(r => r.data);

export const deleteNote = (id) =>
    api.delete(`${BASE}/${id}`).then(r => r.data);
