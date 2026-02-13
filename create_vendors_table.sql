-- Create vendors table based on frontend formData structure
-- This table stores all vendor registration data exactly as sent from frontend

CREATE TABLE IF NOT EXISTS vendors (
    registration_id VARCHAR(50) PRIMARY KEY,
    
    -- Step 1: Basic Details
    hotel_name VARCHAR(150) NOT NULL,
    hotel_type VARCHAR(50),
    year_of_establishment VARCHAR(4),
    total_rooms VARCHAR(10),
    owner_name VARCHAR(100) NOT NULL,
    mobile_number VARCHAR(20) NOT NULL,
    alternate_contact VARCHAR(20),
    landline_numbers JSONB,
    email VARCHAR(150),
    website VARCHAR(255),
    
    -- Step 2: Address
    address_line1 VARCHAR(255) NOT NULL,
    address_line2 VARCHAR(255),
    city VARCHAR(100) NOT NULL,
    district VARCHAR(100),
    state VARCHAR(100) NOT NULL,
    pin_code VARCHAR(6) NOT NULL,
    landmark VARCHAR(255),
    
    -- Step 3: Room Details
    selected_room_types JSONB,
    room_details JSONB,
    min_tariff VARCHAR(20),
    max_tariff VARCHAR(20),
    extra_bed_available BOOLEAN,
    
    -- Step 4: Amenities
    basic_amenities JSONB,
    hotel_facilities JSONB,
    food_services JSONB,
    additional_amenities JSONB,
    custom_amenities JSONB,
    
    -- Step 4: Legal Documents
    gst_number VARCHAR(50),
    fssai_license VARCHAR(50),
    trade_license VARCHAR(50),
    pan_number VARCHAR(20),
    aadhar_number VARCHAR(20),
    
    -- Step 5: Bank Details
    account_holder_name VARCHAR(100) NOT NULL,
    bank_name VARCHAR(100) NOT NULL,
    account_number VARCHAR(30) NOT NULL,
    ifsc_code VARCHAR(11) NOT NULL,
    branch VARCHAR(100),
    account_type VARCHAR(20),
    
    -- Step 5: Documents
    uploaded_files JSONB,
    signature_name VARCHAR(100),
    declaration_name VARCHAR(100),
    declaration_date TIMESTAMP,
    person_photo_info JSONB,
    declaration_accepted BOOLEAN NOT NULL DEFAULT false,
    
    -- Status
    registration_status VARCHAR(20) DEFAULT 'PENDING',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes
CREATE INDEX IF NOT EXISTS idx_vendors_email ON vendors(email);
CREATE INDEX IF NOT EXISTS idx_vendors_mobile ON vendors(mobile_number);
CREATE INDEX IF NOT EXISTS idx_vendors_registration_id ON vendors(registration_id);
CREATE INDEX IF NOT EXISTS idx_vendors_city ON vendors(city);
CREATE INDEX IF NOT EXISTS idx_vendors_status ON vendors(registration_status);

-- Add comments
COMMENT ON TABLE vendors IS 'Vendor registration table matching frontend formData structure exactly';
COMMENT ON COLUMN vendors.registration_id IS 'Unique registration ID format: VRyyyyMM0001';
COMMENT ON COLUMN vendors.landline_numbers IS 'JSONB array of landline numbers';
COMMENT ON COLUMN vendors.selected_room_types IS 'JSONB map of room types and their selection status';
COMMENT ON COLUMN vendors.room_details IS 'JSONB nested map containing room details (rooms, occupancy, ac, price, extraBed, extraBedPrice)';
COMMENT ON COLUMN vendors.basic_amenities IS 'JSONB map of basic amenities and their availability';
COMMENT ON COLUMN vendors.hotel_facilities IS 'JSONB map of hotel facilities and their availability';
COMMENT ON COLUMN vendors.food_services IS 'JSONB map of food services and their availability';
COMMENT ON COLUMN vendors.additional_amenities IS 'JSONB map of additional amenities and their availability';
COMMENT ON COLUMN vendors.custom_amenities IS 'JSONB array of custom amenities';
COMMENT ON COLUMN vendors.uploaded_files IS 'JSONB nested map containing uploaded file information (name, size, path, base64, type, uploaded)';
COMMENT ON COLUMN vendors.person_photo_info IS 'JSONB map containing person photo information (name, size, path, base64, type, uploaded)';

