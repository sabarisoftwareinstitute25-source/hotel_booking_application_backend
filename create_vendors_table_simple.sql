-- Create vendors table for Account Details (Full Name, Business Name, Phone/Email)
CREATE TABLE IF NOT EXISTS vendors (
    vendor_id VARCHAR(50) PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    business_name VARCHAR(150) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(150),
    password VARCHAR(255),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- Indexes for faster lookups
    CONSTRAINT unique_email UNIQUE (email),
    CONSTRAINT unique_phone UNIQUE (phone)
);

-- Create index for faster searches
CREATE INDEX IF NOT EXISTS idx_vendors_email ON vendors(email);
CREATE INDEX IF NOT EXISTS idx_vendors_phone ON vendors(phone);
CREATE INDEX IF NOT EXISTS idx_vendors_status ON vendors(status);

