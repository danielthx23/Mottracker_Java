-- Fix estado_moto column type from NUMBER to VARCHAR2
-- This migration handles the conversion from ORDINAL to STRING enum mapping

-- Step 1: Add a temporary column
ALTER TABLE mt_moto_java ADD estado_moto_temp VARCHAR2(50);

-- Step 2: Convert existing data from numbers to enum names
UPDATE mt_moto_java SET estado_moto_temp = 
    CASE estado_moto
        WHEN 0 THEN 'NO_PATIO'
        WHEN 1 THEN 'RETIRADA'
        WHEN 2 THEN 'MANUTENCAO'
        WHEN 3 THEN 'VENDIDA'
        ELSE 'NO_PATIO'
    END;

-- Step 3: Drop the old column
ALTER TABLE mt_moto_java DROP COLUMN estado_moto;

-- Step 4: Rename the temporary column
ALTER TABLE mt_moto_java RENAME COLUMN estado_moto_temp TO estado_moto;

-- Step 5: Add NOT NULL constraint
ALTER TABLE mt_moto_java MODIFY estado_moto VARCHAR2(50) NOT NULL;
