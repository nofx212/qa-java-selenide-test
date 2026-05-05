SELECT q.id, q.title, a.id AS answer_id, a.title AS answer_title, a.created_at
FROM tbl_questions q
         INNER JOIN tbl_answers a ON q.id = a.question_id
WHERE q.title = ?
  AND a.id = (
    SELECT MAX(id)
    FROM tbl_answers
    WHERE question_id = q.id
);