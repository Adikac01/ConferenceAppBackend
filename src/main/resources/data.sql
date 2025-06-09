-- Inputy dla tabeli 'event_types'
-- Upewnij się, że te typy nie istnieją, zanim je wstawisz, aby uniknąć duplikatów.
-- Możesz dodać klauzule ON CONFLICT, jeśli używasz PostgreSQL 9.5+
INSERT INTO event_types (name, color, icon) VALUES
('Keynote', '#FF0000', 'mic'), -- Przykładowe kolory i ikony
('Tech', '#007bff', 'laptop'),
('Security', '#dc3545', 'shield'),
('Workshop', '#28a745', 'tools'),
('Panel', '#6f42c1', 'users')
ON CONFLICT (name) DO NOTHING; -- Zapobiega duplikatom, jeśli typy już istnieją
-- Inputy dla tabeli 'events'
-- Zakładam, że conference_id dla wszystkich wydarzeń to 1 (utworzone w kroku 1)
-- type_id będzie mapowane na podstawie nazwy typu z tabeli event_types

INSERT INTO events (id, conference_id, type_id, name, description, start_time, end_time, room, max_attendees, speaker_info) VALUES
(1, 1, (SELECT id FROM event_types WHERE name = 'Keynote'), 'Opening Keynote', 'Kickoff the conference with inspiring tech visions.', '2025-04-15 09:00:00', '2025-04-15 10:00:00', 'Main Hall', NULL, 'Alex Johnson'),
(2, 1, (SELECT id FROM event_types WHERE name = 'Tech'), 'The Rise of Quantum Computing', 'Introduction to quantum computing breakthroughs.', '2025-04-16 10:30:00', '2025-04-16 11:30:00', 'Room 1B', NULL, 'Dr. Emily Stone'),
(3, 1, (SELECT id FROM event_types WHERE name = 'Security'), 'Cybersecurity in 2025', 'Protecting data in an increasingly connected world.', '2025-04-18 13:00:00', '2025-04-18 14:00:00', 'Room 3C', NULL, 'Marcus Reed'),
(4, 1, (SELECT id FROM event_types WHERE name = 'Tech'), 'The Future of Web3', 'Decentralization and the next-gen internet.', '2025-04-20 11:00:00', '2025-04-20 12:00:00', 'Innovation Hub', NULL, 'Sophia Torres'),
(5, 1, (SELECT id FROM event_types WHERE name = 'Workshop'), 'Building Scalable Apps', 'Workshop on cloud-native architecture for scale.', '2025-04-22 14:30:00', '2025-04-22 16:30:00', 'Room 2A', NULL, 'Ethan Cole'),
(6, 1, (SELECT id FROM event_types WHERE name = 'Panel'), 'Women in Tech: Panel Discussion', 'Celebrating diversity and leadership in tech.', '2025-04-25 15:00:00', '2025-04-25 16:00:00', 'Main Hall', NULL, 'Various Speakers'),
(7, 1, (SELECT id FROM event_types WHERE name = 'Workshop'), 'Machine Learning Crash Course', 'Hands-on session on machine learning basics.', '2025-04-27 10:00:00', '2025-04-27 12:00:00', 'Room 5D', NULL, 'Laura Cheng'),
(8, 1, (SELECT id FROM event_types WHERE name = 'Security'), 'Cloud Security Deep Dive', 'Securing infrastructures in the cloud era.', '2025-05-01 09:00:00', '2025-05-01 10:30:00', 'Security Lab', NULL, 'Daniel Kim'),
(9, 1, (SELECT id FROM event_types WHERE name = 'Tech'), 'The Power of APIs', 'Designing powerful and flexible APIs.', '2025-05-03 13:00:00', '2025-05-03 14:00:00', 'Room 4B', NULL, 'Sara Patel'),
(10, 1, (SELECT id FROM event_types WHERE name = 'Workshop'), 'DevOps Best Practices', 'Automating and optimizing development pipelines.', '2025-05-05 11:00:00', '2025-05-05 13:00:00', 'Room 6C', NULL, 'Nathan Green'),
(11, 1, (SELECT id FROM event_types WHERE name = 'Panel'), 'The Ethics of AI', 'Navigating ethical dilemmas in artificial intelligence.', '2025-05-08 14:00:00', '2025-05-08 15:30:00', 'Main Hall', NULL, 'Various Experts'),
(12, 1, (SELECT id FROM event_types WHERE name = 'Tech'), '5G and Beyond', 'Exploring the future of connectivity.', '2025-05-10 10:00:00', '2025-05-10 11:00:00', 'Innovation Hub', NULL, 'Olivia Davis'),
(13, 1, (SELECT id FROM event_types WHERE name = 'Tech'), 'VR/AR: The New Frontier', 'How virtual and augmented reality are reshaping industries.', '2025-05-14 15:00:00', '2025-05-14 16:00:00', 'Room 2C', NULL, 'Victor Wang'),
(14, 1, (SELECT id FROM event_types WHERE name = 'Tech'), 'Next-Gen Programming Languages', 'Discover emerging languages and their impacts.', '2025-05-18 13:00:00', '2025-05-18 14:00:00', 'Room 1A', NULL, 'Grace Liu'),
(15, 1, (SELECT id FROM event_types WHERE name = 'Tech'), 'Remote Work Tech Innovations', 'Adapting technology to the future of work.', '2025-06-01 11:00:00', '2025-06-01 12:00:00', 'Room 5B', NULL, 'Chris Bell'),
(16, 1, (SELECT id FROM event_types WHERE name = 'Keynote'), 'Closing Ceremony', 'Wrapping up with reflections and future visions.', '2025-06-14 16:00:00', '2025-06-14 17:00:00', 'Main Hall', NULL, 'Keynote Speaker'),
(17, 1, (SELECT id FROM event_types WHERE name = 'Security'), 'Ethical Hacking 101', 'Fundamentals of ethical hacking and penetration testing.', '2025-05-17 10:00:00', '2025-05-17 11:30:00', 'Room 3A', NULL, 'Natalie Brooks'),
(18, 1, (SELECT id FROM event_types WHERE name = 'Panel'), 'AI in Healthcare', 'How AI is transforming diagnosis and patient care.', '2025-05-18 14:00:00', '2025-05-18 15:30:00', 'Main Hall', NULL, 'Panel of Experts'),
(19, 1, (SELECT id FROM event_types WHERE name = 'Keynote'), 'Tech for Sustainability', 'Innovative solutions for a greener planet.', '2025-05-19 09:00:00', '2025-05-19 10:00:00', 'Main Hall', NULL, 'Dr. Anil Mehta'),
(20, 1, (SELECT id FROM event_types WHERE name = 'Tech'), 'Smart Cities Explained', 'How IoT and data shape the cities of tomorrow.', '2025-05-20 11:00:00', '2025-05-20 12:00:00', 'Room 4A', NULL, 'Isabella Knight'),
(21, 1, (SELECT id FROM event_types WHERE name = 'Workshop'), 'Agile Transformation', 'Practical strategies for embracing agile culture.', '2025-05-22 13:00:00', '2025-05-22 15:00:00', 'Room 6B', NULL, 'Michael Trent'),
(22, 1, (SELECT id FROM event_types WHERE name = 'Tech'), 'Edge Computing Demystified', 'The next step in decentralized computing.', '2025-05-23 10:00:00', '2025-05-23 11:00:00', 'Room 2B', NULL, 'Harper Lin'),
(23, 1, (SELECT id FROM event_types WHERE name = 'Panel'), 'Startup Pitch Showcase', 'Emerging startups pitch their disruptive ideas.', '2025-05-24 15:00:00', '2025-05-24 17:00:00', 'Innovation Hub', NULL, 'Various Startups'),
(24, 1, (SELECT id FROM event_types WHERE name = 'Workshop'), 'Data Science for Everyone', 'Hands-on intro to data science concepts and tools.', '2025-05-25 09:30:00', '2025-05-25 11:30:00', 'Room 1D', NULL, 'Emily Rhodes'),
(25, 1, (SELECT id FROM event_types WHERE name = 'Tech'), 'Next-Gen UI/UX Design', 'Design trends that elevate user experience.', '2025-06-26 11:00:00', '2025-06-26 12:00:00', 'Design Studio', NULL, 'Adrian Foster'),
(26, 1, (SELECT id FROM event_types WHERE name = 'Tech'), 'AI-Powered Customer Support', 'Using NLP and automation for smarter service.', '2025-05-27 14:00:00', '2025-05-27 15:00:00', 'Room 5C', NULL, 'Maya Alston'),
(27, 1, (SELECT id FROM event_types WHERE name = 'Security'), 'Data Privacy in the AI Era', 'Balancing innovation and user privacy.', '2025-06-28 13:00:00', '2025-06-28 14:00:00', 'Security Lab', NULL, 'Liam Novak'),
(28, 1, (SELECT id FROM event_types WHERE name = 'Tech'), 'The Robotics Revolution', 'Exploring advancements in robotics and automation.', '2025-06-29 10:00:00', '2025-06-29 11:00:00', 'Demo Room', NULL, 'Rachel Kim'),
(29, 1, (SELECT id FROM event_types WHERE name = 'Workshop'), 'Inclusive Design Principles', 'Creating products for everyone, by design.', '2025-06-30 12:00:00', '2025-06-30 13:30:00', 'Room 3B', NULL, 'Samuel White'),
(30, 1, (SELECT id FROM event_types WHERE name = 'Security'), 'Post-Quantum Cryptography', 'Preparing for a secure future beyond quantum threats.', '2025-07-01 09:00:00', '2025-07-01 10:00:00', 'Room 2C', NULL, 'Dr. Zoe Grant');

-- Opcjonalnie: Ustawienie sekwencji ID na odpowiednią wartość po ręcznym wstawieniu ID
-- Jeśli tabela 'events' używa SERIAL, po wstawieniu danych z konkretnymi ID,
-- sekwencja może nie być zaktualizowana. To polecenie to naprawia.
-- SELECT setval('events_id_seq', (SELECT MAX(id) FROM events));