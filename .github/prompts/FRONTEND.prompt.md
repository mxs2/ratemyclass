---
mode: agent
---

# RateMyClass Frontend Development Prompt

## Project Overview
Develop an intuitive and responsive frontend application for **RateMyClass**, a platform that enables students to evaluate professors and courses at their university. The interface should provide seamless access to rating functionality, comprehensive search capabilities, and insightful data visualization.

## Core UI/UX Requirements

### 1. Authentication & User Interface
- **Login/Registration Pages**: Clean, university-branded authentication interface
- **Email Verification**: User-friendly email verification flow for university accounts
- **User Dashboard**: Personalized dashboard showing user's submitted reviews, favorite professors/courses
- **Profile Management**: Intuitive profile editing with academic information updates
- **Responsive Design**: Mobile-first approach ensuring seamless experience across all devices

### 2. Navigation & Layout
- **Header Navigation**: Persistent navigation with search bar, user menu, and quick access links
- **Sidebar/Menu**: Collapsible navigation for departments, course categories, and filters
- **Breadcrumb Navigation**: Clear path indication for deep navigation
- **Footer**: University links, contact information, and legal pages
- **Progressive Web App**: PWA capabilities for mobile app-like experience

### 3. Search & Discovery Interface
- **Advanced Search Bar**: Auto-complete functionality for professors and courses
- **Filter Panels**: Dynamic filtering by department, rating range, difficulty, semester
- **Search Results**: Card-based layout with ratings preview, quick stats, and clear CTAs
- **Sorting Options**: Sort by rating, difficulty, recency, alphabetical order
- **Saved Searches**: Allow users to save and revisit frequent searches

### 4. Professor & Course Pages
- **Professor Profiles**: Comprehensive view with photo, department, courses taught, overall ratings
- **Course Details**: Course information, prerequisites, professors who teach it, rating breakdown
- **Rating Distribution**: Visual charts showing rating distributions across different criteria
- **Recent Reviews**: Chronological display of reviews with helpful/unhelpful voting
- **Comparison Tools**: Side-by-side comparison of professors or courses

### 5. Rating & Review Interface
- **Rating Forms**: Intuitive multi-criteria rating system with sliders or star ratings
- **Review Composition**: Rich text editor with character limits and formatting options
- **Anonymous Options**: Clear toggle for anonymous submissions
- **Review Guidelines**: Helpful tips and community guidelines during submission
- **Edit/Delete Controls**: Easy access to modify or remove user's own reviews

## Visual Design Requirements

### 1. Design System
- **Color Palette**: University-themed colors with accessibility-compliant contrast ratios
- **Typography**: Clear, readable fonts with proper hierarchy (headers, body text, captions)
- **Iconography**: Consistent icon system for ratings, subjects, and interface elements
- **Components Library**: Reusable UI components (buttons, cards, forms, modals)
- **Spacing System**: Consistent margins, padding, and grid system

### 2. Data Visualization
- **Rating Charts**: Bar charts, pie charts, and radar charts for rating breakdowns
- **Trend Graphs**: Line charts showing rating trends over time
- **Comparison Views**: Visual comparisons between professors or courses
- **Statistics Dashboard**: Key metrics with intuitive visual representations
- **Interactive Elements**: Hover states, tooltips, and click interactions for charts

### 3. Responsive Breakpoints
- **Mobile (320px-768px)**: Stack layouts, collapsible navigation, touch-friendly buttons
- **Tablet (768px-1024px)**: Two-column layouts, expanded navigation
- **Desktop (1024px+)**: Multi-column layouts, sidebar navigation, hover interactions
- **Large Screens (1440px+)**: Optimized for wide displays without content stretching

## User Experience Features

### 1. Performance & Loading
- **Fast Loading**: Optimize images, implement lazy loading, minimize bundle size
- **Loading States**: Skeleton screens, progress indicators, and smooth transitions
- **Offline Support**: Basic offline functionality with service workers
- **Caching Strategy**: Smart caching for frequently accessed data
- **Error Boundaries**: Graceful error handling with user-friendly messages

### 2. Accessibility
- **WCAG 2.1 AA Compliance**: Full accessibility compliance for all users
- **Keyboard Navigation**: Complete keyboard navigation support
- **Screen Reader Support**: Proper ARIA labels and semantic HTML
- **High Contrast Mode**: Support for users with visual impairments
- **Focus Management**: Clear focus indicators and logical tab order

### 3. Interactive Features
- **Real-time Updates**: Live updates for new ratings and reviews
- **Notifications**: Toast notifications for actions, updates, and errors
- **Bookmarking**: Save favorite professors and courses for quick access
- **Social Features**: Like/dislike reviews, follow professors or courses
- **Export Options**: Export rating data or course lists

## Technical Implementation

## Technical Implementation

### 1. Frontend Technology Stack
- **Framework**: React 18+ with TypeScript (.tsx files)
- **UI Library**: Ant Design (antd) 5.x for consistent, professional components
- **State Management**: Redux Toolkit (RTK) with TypeScript support
- **Routing**: React Router v6 with type-safe route definitions
- **Styling**: Ant Design theme customization with CSS-in-JS (styled-components or emotion)
- **Build Tools**: Vite with TypeScript and hot module replacement
- **Package Manager**: npm or yarn with TypeScript declarations

### 2. Ant Design Implementation Details

#### Project Structure
```
src/
├── components/
│   ├── common/
│   │   ├── Layout/
│   │   │   ├── AppLayout.tsx
│   │   │   ├── Header.tsx
│   │   │   └── Sidebar.tsx
│   │   ├── Rating/
│   │   │   ├── RatingDisplay.tsx
│   │   │   ├── RatingForm.tsx
│   │   │   └── RatingCard.tsx
│   │   └── Search/
│   │       ├── SearchBar.tsx
│   │       ├── FilterPanel.tsx
│   │       └── SearchResults.tsx
│   ├── pages/
│   │   ├── Home/
│   │   │   └── HomePage.tsx
│   │   ├── Professor/
│   │   │   ├── ProfessorProfile.tsx
│   │   │   └── ProfessorList.tsx
│   │   ├── Course/
│   │   │   ├── CourseProfile.tsx
│   │   │   └── CourseList.tsx
│   │   └── Auth/
│   │       ├── LoginPage.tsx
│   │       └── RegisterPage.tsx
│   └── charts/
│       ├── RatingChart.tsx
│       ├── TrendChart.tsx
│       └── ComparisonChart.tsx
├── hooks/
│   ├── useAuth.ts
│   ├── useRatings.ts
│   └── useSearch.ts
├── services/
│   ├── api.ts
│   ├── authService.ts
│   └── ratingService.ts
├── store/
│   ├── index.ts
│   ├── authSlice.ts
│   ├── ratingSlice.ts
│   └── searchSlice.ts
├── types/
│   ├── auth.ts
│   ├── rating.ts
│   ├── professor.ts
│   └── course.ts
├── utils/
│   ├── constants.ts
│   ├── helpers.ts
│   └── validation.ts
└── styles/
    ├── theme.ts
    ├── globals.css
    └── antd-overrides.css
```

#### Ant Design Component Usage Examples

**Search Interface with Ant Design:**
```tsx
import { Input, Select, Card, Rate, Tag, Space, Button } from 'antd';
import { SearchOutlined, FilterOutlined } from '@ant-design/icons';

interface SearchBarProps {
  onSearch: (query: string) => void;
  onFilter: (filters: FilterOptions) => void;
}

const SearchBar: React.FC<SearchBarProps> = ({ onSearch, onFilter }) => {
  return (
    <Card>
      <Space.Compact style={{ width: '100%' }}>
        <Input.Search
          placeholder="Search professors or courses..."
          allowClear
          enterButton={<SearchOutlined />}
          size="large"
          onSearch={onSearch}
        />
        <Button 
          type="default" 
          icon={<FilterOutlined />}
          onClick={() => {/* Open filter modal */}}
        >
          Filters
        </Button>
      </Space.Compact>
    </Card>
  );
};
```

**Rating Component with Ant Design:**
```tsx
import { Card, Rate, Progress, Typography, Space, Divider } from 'antd';
import { StarFilled } from '@ant-design/icons';

interface RatingDisplayProps {
  overallRating: number;
  criteria: {
    teaching: number;
    difficulty: number;
    workload: number;
    clarity: number;
  };
  reviewCount: number;
}

const RatingDisplay: React.FC<RatingDisplayProps> = ({ 
  overallRating, 
  criteria, 
  reviewCount 
}) => {
  return (
    <Card title="Rating Overview">
      <Space direction="vertical" style={{ width: '100%' }}>
        <div style={{ textAlign: 'center' }}>
          <Typography.Title level={2}>{overallRating.toFixed(1)}</Typography.Title>
          <Rate disabled defaultValue={overallRating} />
          <Typography.Text type="secondary">
            Based on {reviewCount} reviews
          </Typography.Text>
        </div>
        
        <Divider />
        
        <Space direction="vertical" style={{ width: '100%' }}>
          <div>
            <Typography.Text>Teaching Quality</Typography.Text>
            <Progress percent={criteria.teaching * 20} showInfo={false} />
          </div>
          <div>
            <Typography.Text>Difficulty Level</Typography.Text>
            <Progress percent={criteria.difficulty * 20} showInfo={false} />
          </div>
          <div>
            <Typography.Text>Workload</Typography.Text>
            <Progress percent={criteria.workload * 20} showInfo={false} />
          </div>
          <div>
            <Typography.Text>Clarity</Typography.Text>
            <Progress percent={criteria.clarity * 20} showInfo={false} />
          </div>
        </Space>
      </Space>
    </Card>
  );
};
```

**Professor Profile with Ant Design:**
```tsx
import { 
  Card, 
  Avatar, 
  Typography, 
  Tag, 
  Row, 
  Col, 
  Statistic, 
  Button,
  Divider 
} from 'antd';
import { UserOutlined, BookOutlined } from '@ant-design/icons';

interface ProfessorProfileProps {
  professor: Professor;
  ratings: RatingData;
  courses: Course[];
}

const ProfessorProfile: React.FC<ProfessorProfileProps> = ({ 
  professor, 
  ratings, 
  courses 
}) => {
  return (
    <div>
      <Card>
        <Row gutter={24}>
          <Col span={6}>
            <Avatar size={120} icon={<UserOutlined />} src={professor.photoUrl} />
          </Col>
          <Col span={18}>
            <Typography.Title level={2}>{professor.name}</Typography.Title>
            <Typography.Text type="secondary">{professor.department}</Typography.Text>
            <div style={{ marginTop: 16 }}>
              <Tag color="blue">{professor.title}</Tag>
              <Tag color="green">{courses.length} Courses</Tag>
            </div>
          </Col>
        </Row>
      </Card>

      <Row gutter={16} style={{ marginTop: 24 }}>
        <Col span={8}>
          <Card>
            <Statistic
              title="Overall Rating"
              value={ratings.overall}
              precision={1}
              suffix="/ 5"
            />
          </Card>
        </Col>
        <Col span={8}>
          <Card>
            <Statistic
              title="Total Reviews"
              value={ratings.count}
              prefix={<BookOutlined />}
            />
          </Card>
        </Col>
        <Col span={8}>
          <Card>
            <Statistic
              title="Would Take Again"
              value={ratings.wouldTakeAgain}
              suffix="%"
            />
          </Card>
        </Col>
      </Row>
    </div>
  );
};
```

#### Theme Customization
```tsx
// theme.ts
import { ThemeConfig } from 'antd';

export const theme: ThemeConfig = {
  token: {
    // University brand colors
    colorPrimary: '#1890ff',
    colorSuccess: '#52c41a',
    colorWarning: '#faad14',
    colorError: '#ff4d4f',
    
    // Typography
    fontFamily: 'Inter, -apple-system, BlinkMacSystemFont, sans-serif',
    fontSize: 14,
    
    // Spacing
    borderRadius: 8,
    wireframe: false,
  },
  components: {
    Card: {
      borderRadius: 12,
      boxShadow: '0 2px 8px rgba(0, 0, 0, 0.1)',
    },
    Button: {
      borderRadius: 6,
      fontWeight: 500,
    },
    Rate: {
      colorFillContent: '#fadb14',
    },
  },
};
```

### 3. TypeScript Integration
- **Type-Safe API Calls**: Strongly typed service layer with interface definitions
- **Component Props**: Comprehensive TypeScript interfaces for all component props
- **State Management**: Typed Redux store with RTK Query for API integration
- **Form Validation**: Ant Design Form with TypeScript validation schemas
- **Route Parameters**: Type-safe routing with parameter validation

```tsx
// Example TypeScript interfaces
interface Professor {
  id: number;
  name: string;
  department: string;
  title: string;
  photoUrl?: string;
  overallRating: number;
  reviewCount: number;
  courses: Course[];
}

interface RatingFormData {
  professorId: number;
  courseId: number;
  overallRating: number;
  teachingQuality: number;
  difficulty: number;
  workload: number;
  clarity: number;
  review: string;
  isAnonymous: boolean;
  wouldTakeAgain: boolean;
}

// API service with types
class RatingService {
  static async submitRating(data: RatingFormData): Promise<ApiResponse<Rating>> {
    return api.post<ApiResponse<Rating>>('/api/v1/ratings', data);
  }
  
  static async getProfessorRatings(
    professorId: number, 
    pagination: PaginationParams
  ): Promise<ApiResponse<PaginatedRatings>> {
    return api.get<ApiResponse<PaginatedRatings>>(
      `/api/v1/ratings/professor/${professorId}`,
      { params: pagination }
    );
  }
}
```

### 4. Integration Requirements
- **API Integration**: Axios with TypeScript for RESTful API consumption and error handling
- **Authentication**: JWT token management with Ant Design's message components for feedback
- **Real-time Features**: WebSocket integration for live updates using socket.io-client
- **Form Handling**: Ant Design Form components with async validation and TypeScript
- **Error Boundaries**: React error boundaries with Ant Design Result components for error display

### 5. Performance Optimization
- **Code Splitting**: React.lazy() for route-based and component-based lazy loading
- **Ant Design Optimization**: Tree-shaking and selective imports to reduce bundle size
- **Image Optimization**: WebP format with Ant Design's Image component
- **Bundle Analysis**: Webpack Bundle Analyzer or Vite Bundle Analyzer
- **Caching Headers**: Proper browser caching with service workers
- **Virtual Scrolling**: Ant Design's Table and List virtualization for large datasets

```tsx
// Example of optimized Ant Design imports
import { Button, Card, Rate } from 'antd';
// Instead of: import * from 'antd';

// Lazy loading with React.Suspense
const ProfessorProfile = lazy(() => import('./pages/Professor/ProfessorProfile'));
const CourseProfile = lazy(() => import('./pages/Course/CourseProfile'));

// Virtual table for large datasets
import { Table } from 'antd';
const VirtualTable = Table as any; // Use Ant Design's virtual table

const ProfessorList: React.FC = () => {
  return (
    <VirtualTable
      columns={columns}
      dataSource={professors}
      scroll={{ y: 400, x: 1000 }}
      pagination={{ pageSize: 50 }}
    />
  );
};
```

## Key Pages & Components

### 1. Core Pages
- **Home Page**: Search interface, featured professors/courses, quick stats
- **Search Results**: Filtered listings with sorting and pagination
- **Professor Profile**: Detailed view with ratings, reviews, and courses
- **Course Profile**: Course details with professor listings and ratings
- **Submit Review**: Rating form with validation and guidelines
- **User Dashboard**: Personal ratings, bookmarks, and account settings

### 2. Essential Components
- **Search Bar**: Auto-complete with recent searches
- **Rating Display**: Star ratings with numerical values
- **Review Card**: Individual review with metadata and actions
- **Filter Panel**: Dynamic filtering with clear/reset options
- **Chart Components**: Reusable data visualization components
- **Modal Dialogs**: Confirmation dialogs, image viewers, detailed forms

## Success Criteria
- ✅ Intuitive user interface with minimal learning curve
- ✅ Fast search functionality with immediate results
- ✅ Responsive design working flawlessly on all devices
- ✅ Accessible to users with disabilities (WCAG 2.1 AA)
- ✅ Loading times under 3 seconds on average connections
- ✅ High user engagement with rating submission flow
- ✅ Clear data visualization that provides actionable insights
- ✅ Seamless integration with backend API
- ✅ Cross-browser compatibility (Chrome, Firefox, Safari, Edge)
- ✅ Progressive Web App functionality

## Implementation Considerations
- **React 18+ with TypeScript** for modern React features and type safety
- **Ant Design 5.x** with theme customization to match university branding
- **Vite** for fast development and optimized production builds
- **ESLint + Prettier** with TypeScript rules for code quality
- **Testing strategy**: Jest + React Testing Library + @testing-library/user-event
- **Responsive design**: Ant Design's Grid system and responsive utilities
- **Accessibility**: Ant Design's built-in ARIA support with custom accessibility enhancements
- **PWA capabilities**: Service workers with Workbox for offline functionality
- **Internationalization**: Ant Design's i18n support for multi-language capability
- **CI/CD pipeline**: GitHub Actions with TypeScript compilation and testing