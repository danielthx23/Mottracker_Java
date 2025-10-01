-- Update estado_moto data to ensure correct values
-- This migration ensures all estado_moto values are properly set

UPDATE mt_moto_java 
SET estado_moto = 'NO_PATIO' 
WHERE estado_moto IS NULL OR estado_moto = '';

-- Set specific motos to NO_PATIO if they are in a patio
UPDATE mt_moto_java 
SET estado_moto = 'NO_PATIO' 
WHERE moto_patio_atual_id IS NOT NULL 
AND (estado_moto IS NULL OR estado_moto = '' OR estado_moto NOT IN ('RETIRADA', 'NO_PATIO', 'NO_PATIO_ERRADO', 'NAO_DEVOLVIDA'));
