using Caliburn.Micro;

namespace Tickets.ViewModels
{
    public class SignInPageViewModel : PropertyChangedBase
    {
        private readonly INavigationService _navigationService;

        public SignInPageViewModel()
        {

        }

        public SignInPageViewModel(INavigationService navigationService)
        {
            _navigationService = navigationService;
        }

        public void SignIn()
        {
            _navigationService.For<DashboardPageViewModel>().Navigate();
        }
    }
}
