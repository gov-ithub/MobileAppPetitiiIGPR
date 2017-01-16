using System.Windows.Markup;
using Tickets.Resources;
using Tickets.ViewModels;

namespace Tickets
{
    using Caliburn.Micro;
    using Microsoft.Phone.Controls;
    using System;
    using System.Collections.Generic;
    using System.Windows.Controls;

    public class AppBootstrapper : PhoneBootstrapperBase
    {
        private PhoneContainer container;

        public AppBootstrapper()
        {
            Initialize();
            LogManager.GetLog = type => new DebugLog(type);
        }

        protected override void Configure()
        {
            container = new PhoneContainer();
            if (!Execute.InDesignMode)
                container.RegisterPhoneServices(RootFrame);

            RootFrame.Language = XmlLanguage.GetLanguage(AppResources.ResourceLanguage);

            container.PerRequest<MainPageViewModel>();
            container.PerRequest<SignInPageViewModel>();
            container.PerRequest<SignUpPageViewModel>();

            AddCustomConventions();
        }

        protected override object GetInstance(Type service, string key)
        {
            return container.GetInstance(service, key);
        }

        protected override IEnumerable<object> GetAllInstances(Type service)
        {
            return container.GetAllInstances(service);
        }

        protected override void BuildUp(object instance)
        {
            container.BuildUp(instance);
        }

        private static void AddCustomConventions()
        {
            ConventionManager.AddElementConvention<Pivot>(Pivot.ItemsSourceProperty, "SelectedItem", "SelectionChanged").ApplyBinding =
                (viewModelType, path, property, element, convention) =>
                {
                    if (ConventionManager
                        .GetElementConvention(typeof(ItemsControl))
                        .ApplyBinding(viewModelType, path, property, element, convention))
                    {
                        ConventionManager
                            .ConfigureSelectedItem(element, Pivot.SelectedItemProperty, viewModelType, path);
                        ConventionManager
                            .ApplyHeaderTemplate(element, Pivot.HeaderTemplateProperty, null, viewModelType);
                        return true;
                    }

                    return false;
                };

            ConventionManager.AddElementConvention<Panorama>(Panorama.ItemsSourceProperty, "SelectedItem", "SelectionChanged").ApplyBinding =
                (viewModelType, path, property, element, convention) =>
                {
                    if (ConventionManager
                        .GetElementConvention(typeof(ItemsControl))
                        .ApplyBinding(viewModelType, path, property, element, convention))
                    {
                        ConventionManager
                            .ConfigureSelectedItem(element, Panorama.SelectedItemProperty, viewModelType, path);
                        ConventionManager
                            .ApplyHeaderTemplate(element, Panorama.HeaderTemplateProperty, null, viewModelType);
                        return true;
                    }

                    return false;
                };
        }
    }
}
