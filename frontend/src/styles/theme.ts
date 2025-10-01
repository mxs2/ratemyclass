import { ThemeConfig } from 'antd';

export const theme: ThemeConfig = {
  token: {
    // University brand colors
    colorPrimary: '#1890ff',
    colorSuccess: '#52c41a',
    colorWarning: '#faad14',
    colorError: '#ff4d4f',
    colorInfo: '#1890ff',
    
    // Typography
    fontFamily: 'Inter, -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif',
    fontSize: 14,
    fontSizeHeading1: 38,
    fontSizeHeading2: 30,
    fontSizeHeading3: 24,
    fontSizeHeading4: 20,
    fontSizeHeading5: 16,
    
    // Spacing and sizing
    borderRadius: 8,
    borderRadiusSM: 4,
    borderRadiusLG: 12,
    
    // Colors
    colorBgContainer: '#ffffff',
    colorBgElevated: '#ffffff',
    colorBgLayout: '#f5f5f5',
    colorTextBase: '#000000d9',
    colorTextSecondary: '#00000073',
    colorTextTertiary: '#00000040',
    colorTextQuaternary: '#00000026',
    
    // Shadows
    boxShadow: '0 2px 8px rgba(0, 0, 0, 0.08)',
    boxShadowSecondary: '0 4px 12px rgba(0, 0, 0, 0.12)',
    
    // Layout
    sizeStep: 4,
    sizeUnit: 4,
    wireframe: false,
  },
  
  components: {
    Layout: {
      headerBg: '#ffffff',
      headerHeight: 64,
      headerPadding: '0 24px',
      siderBg: '#001529',
      triggerBg: '#002140',
      triggerColor: '#ffffff',
    },
    
    Card: {
      borderRadius: 12,
      paddingLG: 24,
      boxShadow: '0 2px 8px rgba(0, 0, 0, 0.06)',
      headerBg: 'transparent',
    },
    
    Button: {
      borderRadius: 6,
      fontWeight: 500,
      primaryShadow: '0 2px 4px rgba(24, 144, 255, 0.2)',
    },
    
    Input: {
      borderRadius: 6,
      paddingBlock: 8,
      paddingInline: 12,
    },
    
    Select: {
      borderRadius: 6,
    },
    
    Table: {
      borderRadius: 8,
      headerBg: '#fafafa',
      headerSplitColor: '#f0f0f0',
    },
    
    Modal: {
      borderRadius: 12,
      paddingMD: 24,
    },
    
    Drawer: {
      paddingLG: 24,
    },
    
    Rate: {
      colorFillContent: '#fadb14',
      colorText: '#fadb14',
    },
    
    Progress: {
      defaultColor: '#1890ff',
    },
    
    Tag: {
      borderRadius: 4,
      fontSizeSM: 12,
    },
    
    Statistic: {
      titleFontSize: 14,
      contentFontSize: 24,
    },
    
    Typography: {
      titleMarginBottom: '0.5em',
      titleMarginTop: '1.2em',
    },
    
    Message: {
      contentBg: '#ffffff',
      boxShadow: '0 4px 12px rgba(0, 0, 0, 0.15)',
    },
    
    Notification: {
      paddingMD: 16,
      borderRadius: 8,
    },
    
    Tabs: {
      itemColor: '#00000073',
      itemSelectedColor: '#1890ff',
      itemHoverColor: '#1890ff',
      inkBarColor: '#1890ff',
    },
    
    Menu: {
      itemBg: 'transparent',
      itemSelectedBg: '#e6f7ff',
      itemSelectedColor: '#1890ff',
      itemHoverBg: '#f5f5f5',
      itemHoverColor: '#1890ff',
    },
    
    Breadcrumb: {
      lastItemColor: '#00000073',
      linkColor: '#1890ff',
      linkHoverColor: '#40a9ff',
    },
  },
  
  algorithm: undefined, // Use default algorithm
};

// Dark theme configuration
export const darkTheme: ThemeConfig = {
  ...theme,
  token: {
    ...theme.token,
    colorBgContainer: '#141414',
    colorBgElevated: '#1f1f1f',
    colorBgLayout: '#000000',
    colorTextBase: '#ffffffd9',
    colorTextSecondary: '#ffffff73',
    colorTextTertiary: '#ffffff40',
    colorTextQuaternary: '#ffffff26',
  },
  components: {
    ...theme.components,
    Layout: {
      ...theme.components?.Layout,
      headerBg: '#141414',
      siderBg: '#001529',
    },
    Card: {
      ...theme.components?.Card,
      colorBg: '#141414',
    },
    Table: {
      ...theme.components?.Table,
      headerBg: '#262626',
      colorBgContainer: '#141414',
    },
  },
};

// Responsive breakpoints
export const breakpoints = {
  xs: '480px',
  sm: '576px',
  md: '768px',
  lg: '992px',
  xl: '1200px',
  xxl: '1600px',
} as const;

// Custom CSS variables for additional styling
export const cssVariables = {
  '--color-rating-excellent': '#52c41a',
  '--color-rating-good': '#73d13d',
  '--color-rating-average': '#faad14',
  '--color-rating-poor': '#ff7875',
  '--color-rating-terrible': '#ff4d4f',
  '--shadow-card': '0 2px 8px rgba(0, 0, 0, 0.06)',
  '--shadow-elevated': '0 4px 12px rgba(0, 0, 0, 0.12)',
  '--shadow-dropdown': '0 6px 16px rgba(0, 0, 0, 0.12)',
  '--border-radius-sm': '4px',
  '--border-radius-md': '8px',
  '--border-radius-lg': '12px',
  '--transition-fast': '0.1s',
  '--transition-medium': '0.2s',
  '--transition-slow': '0.3s',
} as const;