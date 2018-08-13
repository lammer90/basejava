select r.uuid, r.full_name,
       c.type, c.value, c.resume_uuid,
       s.type as typeS, s.value as valueS, s.resume_uuid as resume_uuidS
from resume r
left join contact c
    on r.uuid = c.resume_uuid
left join section s
    on r.uuid = s.resume_uuid
where r.uuid = 'uuid1_______________________________'

