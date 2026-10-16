CREATE TABLE IF NOT EXISTS public.company (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    ats_provider VARCHAR(50) NOT NULL,
    ats_token VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_ats_provider_token UNIQUE (ats_provider, ats_token)
);

CREATE TABLE IF NOT EXISTS public.job_posting (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    company_id UUID NOT NULL,
    external_id VARCHAR(255),
    title VARCHAR(500) NOT NULL,
    location_raw VARCHAR(500),
    country_code VARCHAR(10),
    application_url TEXT NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_company_id_external_id UNIQUE (company_id, external_id),
    CONSTRAINT fk_job_posting_company FOREIGN KEY (company_id) REFERENCES public.company (id) ON DELETE CASCADE
);

CREATE INDEX idx_job_posting_country_code ON public.job_posting(country_code);